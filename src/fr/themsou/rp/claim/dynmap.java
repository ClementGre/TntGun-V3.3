package fr.themsou.rp.claim;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.dynmap.markers.AreaMarker;
import org.dynmap.markers.MarkerSet;

import fr.themsou.main.main;

public class dynmap {
	
	
	@SuppressWarnings("unused")
	public void refreshMarkerArea(){
		
		CalculSuperficie CCalculSuperficie = new CalculSuperficie();
		int layer = 100;
		for(MarkerSet marker : main.dynmap.getMarkerAPI().getMarkerSets()){
			marker.deleteMarkerSet();
		}
		
		
		for(String spawn : Claim.getSpawns()){
			layer --;
			
			MarkerSet mApp = null;
			MarkerSet mClaim = null;
			MarkerSet mAgr = null;
			MarkerSet mIns = null;
			
			// SPAWN MARKER
			
			if(main.config.contains("claim.list." + spawn + ".x")){
				MarkerSet m = main.dynmap.getMarkerAPI().createMarkerSet("tntgun.markerset." + spawn, "<b>Ville " + spawn + "</b>", main.dynmap.getMarkerAPI().getMarkerIcons(), false);
				m.setLayerPriority(layer);
				int minx = main.config.getInt("claim.list." + spawn + ".minx");
				int maxx = main.config.getInt("claim.list." + spawn + ".maxx");
				int minz = main.config.getInt("claim.list." + spawn + ".minz");
				int maxz = main.config.getInt("claim.list." + spawn + ".maxz");
				
				ArrayList<String> app = new ArrayList<>();
				
				int[] range3 = {minx, maxx, minz, maxz};
				addMarkerArea(m, range3, "tntgun.markerset." + spawn + ".city", "Ville " + spawn + "", 1, "");
				
			}
			List<Integer> claims = Claim.getSpawnClaims(spawn);

			if(main.config.contains("claim.list." + spawn + ".app")){
				for(String appZone : main.config.getConfigurationSection("claim.list." + spawn + ".app").getKeys(false)){
					
					if(mApp == null){
						mApp = main.dynmap.getMarkerAPI().createMarkerSet("tntgun.markerset." + spawn + ".app", "Appartements", main.dynmap.getMarkerAPI().getMarkerIcons(), false);
						mApp.setLayerPriority(layer);
					}
					
					int minx = main.config.getInt("claim.list." + spawn + ".app." + appZone + ".minx");
					int maxx = main.config.getInt("claim.list." + spawn + ".app." + appZone + ".maxx");
					int minz = main.config.getInt("claim.list." + spawn + ".app." + appZone + ".minz");
					int maxz = main.config.getInt("claim.list." + spawn + ".app." + appZone + ".maxz");

					List<Integer> ids = claims;
					String dsc = "";
					for(int i = 0; i < ids.size(); i++){
						Integer id = ids.get(i);

						int x1 = main.config.getInt("claim.list." + spawn + "." + id + ".x1");	int z1 = main.config.getInt("claim.list." + spawn + "." + id + ".z1");
						int x2 = main.config.getInt("claim.list." + spawn + "." + id + ".x2");	int z2 = main.config.getInt("claim.list." + spawn + "." + id + ".z2");
							
						if(isIn(new Location(Bukkit.getWorld("world"), x1 + (x2 - x1) / 2, 0, z1 + (z2 - z1) / 2), minx, maxx, minz, maxz)){
								
							int sup = CCalculSuperficie.calculSuperficieOfZone(new Location(Bukkit.getWorld("world"), x1, 0, z1), new Location(Bukkit.getWorld("world"), x2, 0, z2));
							String owner = main.config.getString("claim.list." + spawn + "." + id + ".owner");
							List<String> guests = main.config.getStringList("claim.list." + spawn + "." + id + ".guests");
							String type = main.config.getString("claim.list." + spawn + "." + id + ".type");
							boolean sell = main.config.getBoolean("claim.list." + spawn + "." + id + ".sell");
							int price = main.config.getInt("claim.list." + spawn + "." + id + ".price");
							int defPrice = main.config.getInt("claim.list." + spawn + "." + id + ".defprice");
							
							dsc += addSpeedMarker(mApp, null, id, sell, price, defPrice, owner, guests, ClaimType.getClaimType(type).toUserString(), sup, true) + "<br><br>";
							claims.remove(id);
						}
							
					}
					int[] range = {minx, maxx, minz, maxz};
					addMarkerArea(mApp, range, "app." + appZone, appZone + " - Zonne d'immeuble", 4, dsc);

				}
			}

			List<Integer> ids = claims;
			for(int i = 0; i < ids.size(); i++){
				Integer id = ids.get(i);
				
				try{
					if(!id.equals("minx") && !id.equals("maxx") && !id.equals("minz") && !id.equals("maxz") && !id.equals("app") && !id.equals("x") && !id.equals("y") && !id.equals("z")){
						
						int x1 = main.config.getInt("claim.list." + spawn + "." + id + ".x1");
						int z1 = main.config.getInt("claim.list." + spawn + "." + id + ".z1");
						int x2 = main.config.getInt("claim.list." + spawn + "." + id + ".x2");
						int z2 = main.config.getInt("claim.list." + spawn + "." + id + ".z2");
						int sup = CCalculSuperficie.calculSuperficieOfZone(new Location(Bukkit.getWorld("world"), x1, 0, z1), new Location(Bukkit.getWorld("world"), x2, 0, z2));
						
						String owner = main.config.getString("claim.list." + spawn + "." + id + ".owner");
						List<String> guests = main.config.getStringList("claim.list." + spawn + "." + id + ".guests");
						ClaimType type = ClaimType.getClaimType(main.config.getString("claim.list." + spawn + "." + id + ".type"));
						boolean sell = main.config.getBoolean("claim.list." + spawn + "." + id + ".sell");
						int price = main.config.getInt("claim.list." + spawn + "." + id + ".price");
						int defPrice = main.config.getInt("claim.list." + spawn + "." + id + ".defprice");
						
						int[] range = {x1, x2, z1, z2};
						
						if(type.equals(ClaimType.FREE)){
							if(mClaim == null){
								mClaim = main.dynmap.getMarkerAPI().createMarkerSet("tntgun.markerset." + spawn + ".claim", "Claim libres", main.dynmap.getMarkerAPI().getMarkerIcons(), false);
								mClaim.setLayerPriority(layer);
							}
							addSpeedMarker(mClaim, range, id, sell, price, defPrice, owner, guests, type.toUserString(), sup, false);
							
						}else if(type.equals(ClaimType.APARTMENT)){
							if(mApp == null){
								mApp = main.dynmap.getMarkerAPI().createMarkerSet("tntgun.markerset." + spawn + ".app", "Appartements", main.dynmap.getMarkerAPI().getMarkerIcons(), false);
								mApp.setLayerPriority(layer);
							}
							addSpeedMarker(mApp, range, id, sell, price, defPrice, owner, guests, type.toUserString(), sup, false);
							
						}else if(type.equals(ClaimType.FIELD)){
							if(mAgr == null){
								mAgr = main.dynmap.getMarkerAPI().createMarkerSet("tntgun.markerset." + spawn + ".agr", "Zones agricolles", main.dynmap.getMarkerAPI().getMarkerIcons(), false);
								mAgr.setLayerPriority(layer);
							}
							addSpeedMarker(mAgr, range, id, sell, price, defPrice, owner, guests, type.toUserString(), sup, false);
							
						}else if(type.equals(ClaimType.COMPANY)){
							if(mIns == null){
								mIns = main.dynmap.getMarkerAPI().createMarkerSet("tntgun.markerset." + spawn + "ins", "Industries", main.dynmap.getMarkerAPI().getMarkerIcons(), false);
								mIns.setLayerPriority(layer);
							}
							addSpeedMarker(mIns, range, id, sell, price, defPrice, owner, guests, type.toUserString(), sup, false);
							
						}
							
					}
					
				}catch(Exception e){
					e.printStackTrace();
					System.out.println("at claim.list." + spawn + "." + id);
				}
				
				
				
			}

		}
	}
	
	private String addSpeedMarker(MarkerSet markerSet, int[] range, int id, boolean sell, int price, int defPrice, String owner, List<String> guests, String type, int surface, boolean onlyDesc){
		
		int zoneType = 2;
		
		// TITRE
		String title = id + " - " + type;
			
		if(owner != null) title += " de " + owner.split(",")[0];
		
		if(sell){
			title += " à vendre";
			zoneType = 3;
		}

		// DESCRIPTION
		String desc = "<b style=\"color:#08368E\">" + title + "</b><br>";
		
		if(onlyDesc){
			if(sell) desc = "<b style=\"color:#209E10\">" + title + "</b><br>";
			else desc = "<b style=\"color:#AC3335\">" + title + "</b><br>";
		}
		
		
		if(!sell){
			// DESC ENTREPRISES
			if(type.equals("ins")){
				zoneType = 5;
				String entName = owner.split(",")[0];
				List<String> salarié = main.config.getStringList("ent.list." + entName + ".salarié");
				List<String> manager = main.config.getStringList("ent.list." + entName + ".manager");
				String pdg = main.config.getString("ent.list." + entName + ".pdg");
				
				desc += "<b>Entreprise :</b> " + entName + "<br>"
						+ "<b>PDG :</b> " + pdg + "<br>";
			
			// DESC PARTICULIERS
			}else{
				desc += "<b>Propriétaire :</b> " + owner.split(",")[0] + "<br>";
			}
			// DESC INVITÉS
			if(owner.split(",").length >= 2){
				desc += "<b>Invités :</b> ";
				for(String guest : guests){
					desc += guest + ", ";
				}
				desc += "<br>";
			}
		}
		// DESC TOUJOURS PRÉSENTE
		if(price == defPrice) desc += "<b>Prix :</b> " + price + " €<br>" + "<b>Superficie :</b> " + surface + " m²";
		else desc += "<b>Prix :</b> " + price + " €<br>" + "<b>Prix original :</b> " + defPrice + " €<br>" + "<b>Superficie :</b> " + surface + " m²";
		
		// RETURN / PUT
		
		if(onlyDesc){
			return desc;
		}else{
			addMarkerArea(markerSet, range, id+"", title, zoneType, desc);
		}
		
		return null;
		
	}
	
	private boolean isIn(Location loc, int minx, int maxx, int minz, int maxz) {
		
		int x = loc.getBlockX();
		int z = loc.getBlockZ();
		
		if(x <= maxx && x >= minx && z <= maxz && z >= minz){
			
			return true;
			
		}
		
		return false;
	}


	public void addMarkerArea(MarkerSet m, int[] range, String id, String name, int type, String description){ // 1: SPAWNS   2: USE   3: TO SELL   4: APP ZONE   5: ENT USE
		
        AreaMarker am = m.createAreaMarker(id, name, false, "world", new double[0], new double[0], false);
        double[] d1 = {range[0] + 0.5, range[1] + 0.5};
        double[] d2 = {range[2] + 0.5, range[3] + 0.5};
        if(range.length >= 5) am.setRangeY(range[4], range[5]);
        am.setCornerLocations(d1, d2);
        if(type == 1){
        	am.setLineStyle(4, 1, 0x062C74);
            am.setFillStyle(0, 0x062C74);
            am.setRangeY(64, 64);
            
        }else if(type == 2){
        	am.setMinZoom(4);
            am.setLineStyle(3, 1, 0x980103);
            am.setFillStyle(0.3, 0x980103);
            am.setDescription(description);
            am.setRangeY(75, 75);
            
        }else if(type == 3){
        	am.setMinZoom(4);
            am.setLineStyle(3, 1, 0x129801);
            am.setFillStyle(0.3, 0x129801);
            am.setDescription(description);
            am.setRangeY(75, 75);
            
        }else if(type == 4){
        	am.setMinZoom(4);
            am.setLineStyle(3, 1, 0xB1AF24);
            am.setFillStyle(0.3, 0xB1AF24);
            am.setDescription(description);
            am.setRangeY(75, 75);
            
        }else if(type == 5){
        	am.setMinZoom(4);
            am.setLineStyle(3, 1, 0xdfa92a);
            am.setFillStyle(0.3, 0xdfa92a);
            am.setDescription(description);
            am.setRangeY(75, 75);
        }
        am.setLabel(name);
		
	}

}
