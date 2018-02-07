package utils;

import java.util.ArrayList;

import ch.aplu.jgamegrid.Location;
import entity.mind.ExternalPerceptionInterface;

public class PerceptionUtils {
	
	/**
	 * Get entities from perception
	 * 
	 * @param x
	 * @param y
	 * @param bodyType
	 * @param playername
	 * @param perception
	 * @return
	 */
	public ArrayList<ExternalPerceptionInterface> getEntitiesOfPosition(int x, int y, ArrayList<String> bodyType, ArrayList<String> playername, ArrayList<ExternalPerceptionInterface> perception) {
		ArrayList<ExternalPerceptionInterface> result = new ArrayList<ExternalPerceptionInterface>();
		
		for (ExternalPerceptionInterface p : perception) {
			if (p.getXRelativeCoordinate()==x && p.getYRelativeCoordinate()==y) {
				boolean bodytypematch = true;
				boolean playernameMatch = true;
				if (bodyType.isEmpty()==false) {
					bodytypematch = bodyType.contains(p.getObjectBodyType());
				}
				
				if (playername.isEmpty()==false) {
					playernameMatch = playername.contains(p.getObjectName());
				}
				
				if (bodytypematch==true && playernameMatch==true) {
					result.add(p);
				}
			}
		}
		
		return result;
	}
	
	public ArrayList<Location> getPositionOfEntity(ArrayList<String> bodyType, ArrayList<String> playername, ArrayList<ExternalPerceptionInterface> perception, boolean stopAtFirstMatch) {
		ArrayList<Location> result = new ArrayList<Location>();
		
		for (ExternalPerceptionInterface p : perception) {
			boolean bodytypematch = true;
			boolean playernameMatch = true;
			if (bodyType.isEmpty()==false) {
				bodytypematch = bodyType.contains(p.getObjectBodyType());
			}
			
			if (playername.isEmpty()==false) {
				playernameMatch = playername.contains(p.getObjectName());
			}
			
			if (bodytypematch==true && playernameMatch==true) {
				result.add(new Location(p.getXRelativeCoordinate(), p.getYRelativeCoordinate()));
				if (stopAtFirstMatch==true) {
					break;
				}
			}
		}
		
		return result;
	}
	
	public Location getFirstPositionOfEntity(ArrayList<String> bodyType, ArrayList<String> playername, ArrayList<ExternalPerceptionInterface> perception) {
		Location loc = null;
		
		ArrayList<Location> locations = getPositionOfEntity(bodyType, playername, perception, true);
		if (locations.isEmpty()==false) {
			loc = locations.get(0);
		}
		
		return loc;
	}
	
	public Location getClosestLocation(ArrayList<Location> locations) {
		Location result = null;
		Location myLocation = new Location (0,0);
		double bestDistance = 0;
		
		if (locations.isEmpty()==false) {
			result = locations.get(0);
			bestDistance = getDistance(result, myLocation);
		}
		
		for (Location loc : locations) {
			double currentDistance = getDistance(loc, myLocation);
			if (currentDistance<bestDistance) {
				bestDistance = currentDistance;
				result = loc;
			}
		}
		
		return result;
	}
	
	private double getDistance(Location a, Location b) {
		double distance = Math.sqrt(Math.pow(a.x-b.x, 2) + Math.pow(a.y-b.y, 2));
		
		return distance;
	}
}
