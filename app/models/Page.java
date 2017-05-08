package models;

import javax.persistence.*;

/**
 * Created by Lauris on 08/05/2017.
 */
@Entity
public class Page extends com.avaje.ebean.Model {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="page_seq")
    public Long id;

    public Integer pageNumber;

    public String pageContent;

    @ManyToOne(cascade = CascadeType.ALL)
    public Chapter chapter;

    public Page()  { }

    public Page(Long id) {
        this.id = id;
    }

    /**
     * Generic query helper for entity Page with id Long
     */
    public static Find<Long,Page> find = new Find<Long,Page>(){};
}
