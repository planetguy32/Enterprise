package me.planetguy.enterprise.grid;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

import me.planetguy.lib.util.BlockRecord;
import me.planetguy.lib.util.Debug;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.IEnergyReceiver;

public class Search {
	
	private BlockRecord source;
	
	private Search(){}
	
	public static void dispatchEnergy(World w, int x, int y, int z, int current, int voltage){
		List<Pair> paths=findSinks(w,x,y,z);
		int demandCurrent=0;
		for(Pair path:paths){
			int localDC=PowerGrid2dRegistry.getBinding(path.record.tileEntity).getMaximumCurrent(voltage);
			path.demand=localDC;
			demandCurrent+=localDC;
		}
		if(demandCurrent==0)
			return; //check for zero division
		for(Pair path:paths){
			BlockRecord destination=path.record;
			double demandFraction=path.demand/(double)(demandCurrent);
			int currentHere=(int)(current*demandFraction);
			if(currentHere==0)
				continue; //don't go all over trying to give away nothing
			boolean ok=true;
			while(path != null){
				IPowerGrid2dMember grid=PowerGrid2dRegistry.getBinding(path.record.tileEntity);
				if(grid!=null && !grid.onTransport(voltage, currentHere)){
					path=path.previous;
				}else{
					ok=false;
				}
			}
			if(ok){
				IPowerGrid2dMember dest=PowerGrid2dRegistry.getBinding(destination.tileEntity);
				dest.onTransport(currentHere, voltage);
			}
		}
	}

	private static List<Pair> findSinks(World w, int x, int y, int z){
		return new Search().findSinkDijkstra(w,x,y,z);
	}
	
	private List<Pair> findSinkDijkstra(World w, int x, int y, int z){
		TreeSet<BlockRecord> allSearched=new TreeSet<BlockRecord>();
		PriorityQueue<Pair> todos=new PriorityQueue<Pair>();
		List<Pair> dests=new ArrayList<Pair>();
		source=new BlockRecord(x,y,z);
		todos.add(new Pair(source, null));
		while(!todos.isEmpty()){
			Pair next=todos.poll();
			for(ForgeDirection dir:ForgeDirection.VALID_DIRECTIONS){
				BlockRecord r=next.record.NextInDirection(dir);
				if(!allSearched.contains(r)){
					allSearched.add(r);
					r.Identify(w);
					IPowerGrid2dMember bind=PowerGrid2dRegistry.getBinding(r.tileEntity);
					if(bind!=null && bind.canConnectEnergy(dir.getOpposite())){
						Pair p=new Pair(r, next);
						todos.add(p);
						if(bind.getPowerAmountToConsume()>0)
							dests.add(p);
					}
				}
			}
		}
		return dests;
	}
	
	private class Pair implements Comparable<Pair>{
		public final BlockRecord record;
		public final Pair previous;
		public int demand;
		public Pair(BlockRecord r, Pair last){
			record=r;
			previous=last;
		}
		@Override
		public int compareTo(Pair arg0) {
			return arg0.record.distanceSquared(source)-record.distanceSquared(source);
		}
		public String toString(){
			return record+"\n    "+previous;
		}
	}

}
