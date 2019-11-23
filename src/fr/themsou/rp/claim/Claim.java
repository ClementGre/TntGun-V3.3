package fr.themsou.rp.claim;

import fr.themsou.main.main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Claim {

    private boolean exist = false;


    private int id = 0;
    private String spawn = null;

    private String owner;
    private String pureOwner;
    private List<String> guests;
    private ClaimType type;

    private int price = -1;
    private int defPrice = -1;
    private Boolean sell;
    private Boolean needSetup;

    private int x1;
    private int x2;
    private int z1;
    private int z2;
    private int y1;
    private int y2;

    public Claim(Location loc){

        spawn = new Spawns().getSpawnNameWithLoc(loc);
        if(spawn != null){
            id = new GetZoneId().getIdOfPlayerZone(loc);
            if(id != 0 && id != -1){
                exist = true;
            }
        }

    }
    public Claim(int id){
        this.id = id;
        if(id != 0 && id != -1){
            spawn = new Spawns().getSpawnNameWithId(id);
            if(spawn != null){
                exist = true;
            }
        }
    }
    public Claim(int id, String spawn){

        if(id != 0 && id != -1 && spawn != null){
            this.id = id;
            this.spawn = spawn;
        }
    }

    public boolean exist() {
        return exist;
    }
    public int getId() {
        return id;
    }
    public String getSpawn() {
        return spawn;
    }

    public String getOwner() {
        if(getPureOwner() == null) return null;
        if(owner != null){
            return owner;
        }else{
            if(isEnt()){
                return (owner = main.config.getString("ent.list." + getPureOwner() + ".pdg"));
            }else{
                return (owner = main.config.getString("claim.list." + spawn + "." + id + ".owner"));
            }
        }
    }
    public String getPureOwner() {
        if(pureOwner != null){
            return pureOwner;
        }else{
            return (pureOwner = main.config.getString("claim.list." + spawn + "." + id + ".owner"));
        }
    }

    public List<String> getEntOwners() {
        if(!isEnt()){
            return new ArrayList<>();
        }else{
            List<String> owners = new ArrayList<>();
            owners.add(getOwner());
            for(String manager : main.config.getString("ent.list." + getPureOwner() + ".manager").split(",")){
                owners.add(manager);
            }
            for(String salarie : main.config.getString("ent.list." + getPureOwner() + ".salarié").split(",")){
                owners.add(salarie);
            }
            return owners;
        }
    }
    public List<String> getEntSalaries() {
        if(!isEnt()){
            return new ArrayList<>();
        }else{
            List<String> owners = new ArrayList<>();
            for(String salarie : main.config.getString("ent.list." + getPureOwner() + ".salarié").split(",")){
                owners.add(salarie);
            }
            return owners;
        }
    }
    public List<String> getEntManagers() {
        if(!isEnt()){
            return new ArrayList<>();
        }else{
            List<String> owners = new ArrayList<>();
            for(String manager : main.config.getString("ent.list." + getPureOwner() + ".manager").split(",")){
                owners.add(manager);
            }
            return owners;
        }
    }

    public void setOwner(String owner) {
        if(owner == null){
            if(getPureOwner() != null){
                if(isEnt()){
                    removeEntClaim(getPureOwner(), id);
                }else{
                    removePlayerClaim(getPureOwner(), id);
                }
            }
        }else{
            if(isEnt()){
                removeEntClaim(getPureOwner(), id);
                addEntClaim(owner, id);
            }else{
                removePlayerClaim(getPureOwner(), id);
                addPlayerClaim(owner, id);
            }
        }
        setGuests(new ArrayList<>());
        this.owner = owner;
        main.config.set("claim.list." + spawn + "." + id + ".owner", owner);
    }
    public List<String> getGuests(){
        if(guests != null){
            return guests;
        }else{
            return (guests = main.config.getStringList("claim.list." + spawn + "." + id + ".guests"));
        }
    }
    public void setGuests(List<String> guests){
        for(String guest : getGuests()){
            removePlayerClaim(guest, id);
        }
        for(String guest : guests){
            addPlayerClaim(guest, id);
        }

        this.guests = guests;
        main.config.set("claim.list." + spawn + "." + id + ".guests", guests);
    }

    public ClaimType getType() {
        if(type != null){
            return type;
        }else{
            return (type = ClaimType.getClaimType(main.config.getString("claim.list." + spawn + "." + id + ".type")));
        }
    }
    public void setType(ClaimType type) {
        this.type = type;
        main.config.set("claim.list." + spawn + "." + id + ".type", type.toString());
    }

    public int getPrice() {
        if(price != -1){
            return price;
        }else{
            return (price = main.config.getInt("claim.list." + spawn + "." + id + ".price"));
        }
    }
    public void setPrice(int price) {
        this.price = price;
        main.config.set("claim.list." + spawn + "." + id + ".price", price);
    }
    public int getDefPrice() {
        if(defPrice != -1){
            return defPrice;
        }else{
            return (defPrice = main.config.getInt("claim.list." + spawn + "." + id + ".defprice"));
        }
    }
    public void setDefPrice(int defPrice) {
        this.defPrice = defPrice;
        main.config.set("claim.list." + spawn + "." + id + ".defprice", defPrice);
    }

    public Boolean isSell(){
        if(sell != null){
            return sell;
        }else{
            return (sell = main.config.getBoolean("claim.list." + spawn + "." + id + ".sell"));
        }
    }
    public void setSell(boolean sell){
        this.sell = sell;
        main.config.set("claim.list." + spawn + "." + id + ".sell", sell);
    }

    public Boolean getNeedSetup() {
        if(needSetup != null){
            return needSetup;
        }else{
            return (needSetup = main.config.getBoolean("claim.list." + spawn + "." + id + ".needsetup"));
        }
    }
    public void setNeedSetup(Boolean needSetup) {
        this.needSetup = needSetup;
        main.config.set("claim.list." + spawn + "." + id + ".needsetup", needSetup);
    }

    public boolean isEnt(){
        return (getType().equals(ClaimType.COMPANY));
    }
    public boolean is3D(){
        return !(getY2() == -1 && getY1() == -1);
    }

    public int getX1(){
        if(x1 != 0) return x1;
        else return (x1 = main.config.getInt("claim.list." + spawn + "." + id + ".x1"));
    }
    public void setX1(int x1) {
        this.x1 = x1;
        main.config.set("claim.list." + spawn + "." + id + ".x1", x1);
    }
    public int getX2() {
        if(x2 != 0) return x2;
        else return (x2 = main.config.getInt("claim.list." + spawn + "." + id + ".x2"));
    }
    public void setX2(int x2) {
        this.x2 = x2;
        main.config.set("claim.list." + spawn + "." + id + ".x2", x2);
    }
    public int getZ1() {
        if(z1 != 0) return z1;
        else return (z1 = main.config.getInt("claim.list." + spawn + "." + id + ".z1"));
    }
    public void setZ1(int z1) {
        this.z1 = z1;
        main.config.set("claim.list." + spawn + "." + id + ".z1", z1);
    }
    public int getZ2() {
        if(z2 != 0) return z2;
        else return (z2 = main.config.getInt("claim.list." + spawn + "." + id + ".z2"));
    }
    public void setZ2(int z2) {
        this.z2 = z2;
        main.config.set("claim.list." + spawn + "." + id + ".z2", z2);
    }
    public int getY1() {
        if(y1 != 0) return y1;
        else return (y1 = main.config.getInt("claim.list." + spawn + "." + id + ".y1"));
    }
    public void setY1(int y1) {
        this.y1 = y1;
        main.config.set("claim.list." + spawn + "." + id + ".y1", y1);
    }
    public int getY2() {
        if(y2 != 0) return y2;
        else return (y2 = main.config.getInt("claim.list." + spawn + "." + id + ".y2"));
    }
    public void setY2(int y2) {
        this.y2 = y2;
        main.config.set("claim.list." + spawn + "." + id + ".y2", y2);
    }

    public Location getFirstLoc(){
        return new Location(Bukkit.getWorld("world"), getX1(), getY1(), getZ1());
    }
    public Location getSecondLoc(){
        return new Location(Bukkit.getWorld("world"), getX2(), getY2(), getZ2());
    }

    public void setXBounds(int x1, int x2){
        setX1(x1);
        setX2(x2);
    }
    public void setZBounds(int z1, int z2){
        setZ1(z1);
        setZ2(z2);
    }
    public void setYBounds(int y1, int y2){
        setY1(y1);
        setY2(y2);
    }

    public void setFull2DBounds(int x1, int x2, int z1, int z2){
        setXBounds(x1, x2);
        setZBounds(z1, z2);
    }
    public void setFull3DBounds(int x1, int x2, int z1, int z2, int y1, int y2){
        setXBounds(x1, x2);
        setZBounds(z1, z2);
        setYBounds(y1, y2);
    }
    public void setFull2DBounds(Location loc1, Location loc2){
        setXBounds(loc1.getBlockX(), loc2.getBlockX());
        setZBounds(loc1.getBlockZ(), loc2.getBlockZ());
    }
    public void setFull3DBounds(Location loc1, Location loc2){
        setXBounds(loc1.getBlockX(), loc2.getBlockX());
        setZBounds(loc1.getBlockZ(), loc2.getBlockZ());
        setYBounds(loc1.getBlockY(), loc2.getBlockY());
    }

    public static String[] getSpawns(){
        Set<String> spawnsSet = main.config.getConfigurationSection("claim.list").getKeys(false);
        String[] spawns = new String[spawnsSet.size()];
        spawnsSet.toArray(spawns);
        return spawns;
    }
    public static List<Integer> getSpawnClaims(String spawn){

        List<Integer> ids = new ArrayList<>();

        for(String id : main.config.getConfigurationSection("claim.list." + spawn).getKeys(false)){

            if(id.equals("minx") || id.equals("maxx") || id.equals("minz") || id.equals("maxz") || id.equals("app") || id.equals("x") || id.equals("y") || id.equals("z")) continue;

            try{
                ids.add(Integer.parseInt(id));
            }catch(NumberFormatException ignored){}
        }
        return ids;
    }
    public static List<Integer> getAllClaims(){

        List<Integer> ids = new ArrayList<>();

        for(String spawn : main.config.getConfigurationSection("claim.list").getKeys(false)){
            for(String id : main.config.getConfigurationSection("claim.list." + spawn).getKeys(false)){

                if(id.equals("minx") || id.equals("maxx") || id.equals("minz") || id.equals("maxz") || id.equals("app") || id.equals("x") || id.equals("y") || id.equals("z")) continue;

                try{
                    ids.add(Integer.parseInt(id));
                }catch(NumberFormatException ignored){}
            }
        }
        return ids;
    }

    public static void setPlayerClaims(String player, List<Integer> ids){
        main.config.set(player + ".rp.claims.list", ids);
    }
    public static void addPlayerClaim(String player, int id){
        List<Integer> ids = getPlayerClaims(player);
        if(!ids.contains(id)) ids.add(id);
        setPlayerClaims(player, ids);

    }
    public static void removePlayerClaim(String player, int id){

        List<Integer> ids = getPlayerClaims(player);
        ids.remove((Integer) id);
        setPlayerClaims(player, ids);
    }
    public static boolean hasPlayerClaim(String player, int id){
        return getPlayerClaims(player).contains(id);
    }
    public static List<Integer> getPlayerClaims(String player){
        return main.config.getIntegerList(player + ".rp.claims.list");
    }

    public static void setEntClaims(String ent, List<Integer> ids){
        main.config.set("ent.list." + ent + ".claims", ids);
    }
    public static void addEntClaim(String ent, int id){
        List<Integer> ids = getEntClaims(ent);
        if(!ids.contains(id)) ids.add(id);
        setEntClaims(ent, ids);

    }
    public static void removeEntClaim(String ent, int id){
        List<Integer> ids = getEntClaims(ent);
        ids.remove((Integer) id);
        setEntClaims(ent, ids);
    }
    public static boolean hasEntClaim(String ent, int id){
        return getEntClaims(ent).contains(id);
    }
    public static List<Integer> getEntClaims(String ent){
        return main.config.getIntegerList("ent.list." + ent + ".claims");
    }

}
