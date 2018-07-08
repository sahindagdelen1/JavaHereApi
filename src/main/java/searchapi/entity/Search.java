package searchapi.entity;

public class Search {

    private Context context;
    private Boolean supportsPanning;
    private String ranking;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Boolean getSupportsPanning() {
        return supportsPanning;
    }

    public void setSupportsPanning(Boolean supportsPanning) {
        this.supportsPanning = supportsPanning;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

}
