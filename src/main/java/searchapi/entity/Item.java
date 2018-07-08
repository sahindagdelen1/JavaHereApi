package searchapi.entity;

import java.util.ArrayList;
import java.util.List;

public class Item {

    private List<Double> position = new ArrayList<Double>();
    private Integer distance;
    private String title;
    private Integer averageRating;
    private Category category;
    private String icon;
    private String vicinity;
    private List<Object> having = new ArrayList<Object>();
    private String type;
    private String href;
    private List<Tag> tags = new ArrayList<Tag>();
    private String id;
    private List<AlternativeName> alternativeNames = new ArrayList<AlternativeName>();
    private List<String> chainIds = new ArrayList<String>();

    public List<Double> getPosition() {
        return position;
    }

    public void setPosition(List<Double> position) {
        this.position = position;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Integer averageRating) {
        this.averageRating = averageRating;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public List<Object> getHaving() {
        return having;
    }

    public void setHaving(List<Object> having) {
        this.having = having;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<AlternativeName> getAlternativeNames() {
        return alternativeNames;
    }

    public void setAlternativeNames(List<AlternativeName> alternativeNames) {
        this.alternativeNames = alternativeNames;
    }

    public List<String> getChainIds() {
        return chainIds;
    }

    public void setChainIds(List<String> chainIds) {
        this.chainIds = chainIds;
    }

}
