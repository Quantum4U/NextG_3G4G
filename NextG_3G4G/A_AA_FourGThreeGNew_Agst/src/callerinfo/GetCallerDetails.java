package callerinfo;


public class GetCallerDetails {
    public String OPARATOR_NAME;
    public String LOCATION;

    public GetCallerDetails getDetails(String data) {
        if (data.length() > 25) {
            GetCallerDetails getCallerDetails = new GetCallerDetails();
            //Bharti Airtel Limited,Punjab,10
            data = data.substring(0, data.indexOf(",1"));
            String subdata[] = data.split(",");
            if (subdata != null & subdata.length > 0)
                getCallerDetails.OPARATOR_NAME = subdata[0];
            else
                getCallerDetails.OPARATOR_NAME = "Unknown";

            if (subdata != null & subdata.length > 1)
                getCallerDetails.LOCATION = subdata[1];
            else
                getCallerDetails.LOCATION = "Unknown";

            return getCallerDetails;

        } else
            return null;


    }

}
