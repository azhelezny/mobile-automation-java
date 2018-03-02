package framework.rest.models.github_contributors;

import framework.rest.models.commons.MAuthor;
import framework.rest.models.commons.MWeek;

class MGithubResponse {
    private Integer total;
    private MAuthor author;
    private MWeek[] weeks;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public MAuthor getAuthor() {
        return author;
    }

    public void setAuthor(MAuthor author) {
        this.author = author;
    }

    public MWeek[] getWeeks() {
        return weeks;
    }

    public void setWeeks(MWeek[] weeks) {
        this.weeks = weeks;
    }
}
