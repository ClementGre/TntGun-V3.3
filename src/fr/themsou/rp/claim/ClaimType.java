package fr.themsou.rp.claim;

public enum ClaimType {
    FREE("claim"),
    APARTMENT("app"),
    FIELD("agr"),
    COMPANY("ins");

    String type;
    ClaimType(String type){
        this.type = type;
    }
    public String toString(){
        return type;
    }

    public String toUserString(){
        if(type.equals("app")) return "Appartement";
        if(type.equals("agr")) return "Agricolle";
        if(type.equals("ins")) return "Entreprise";
        return "Claim libre";
    }

    public static ClaimType getClaimType(String type){
        if(type == null) return ClaimType.FREE;
        switch (type){
            case "app":
                return ClaimType.APARTMENT;
            case "agr":
                return ClaimType.FIELD;
            case "ins":
                return ClaimType.COMPANY;
            default:
                return ClaimType.FREE;
        }
    }

}
