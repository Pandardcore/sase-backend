package controllers;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Transaction;
import models.Page;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Lauris on 08/05/2017.
 */
public class PageController extends Controller {

    private FormFactory formFactory;

    @Inject
    public PageController(FormFactory formFactory) {
        this.formFactory = formFactory;
    }

    public Result chapterPages(Long bookId, Integer chapNumber) {
        List<Page> pages = Page.find.where()
                .eq("chapter.chapNumber", chapNumber)
                .eq("chapter.book.id", bookId)
                .order().asc("chapter.chapNumber")
                .order().asc("pageNumber")
                .findList();
        if(pages != null)  {
            return ok(Json.toJson(pages));
        }
        return notFound();
    }

    public Result pageById(Long id) {
        Page requestedPage = Page.find.byId(id);
        if(requestedPage != null) {
            return ok(Json.toJson(requestedPage));
        }
        return notFound();
    }

    public Result chapterPageByNumber(Long bookId, Integer chapNumber, Integer pageNumber) {
        Page page = Page.find.where()
                .eq("pageNumber", pageNumber)
                .eq("chapter.chapNumber", chapNumber)
                .eq("chapter.book.id", bookId)
                .findUnique();
        if(page != null) {
            return ok(Json.toJson(page));
        }
        return notFound();
    }



    public Result create() {
        Form<Page> pageForm = formFactory.form(Page.class).bindFromRequest();
        if(pageForm.hasErrors()) {
            return badRequest(pageForm.errorsAsJson());
        }
        pageForm.get().save();
        return ok();
    }

    public Result update(Long id) {
        Form<Page> pageForm = formFactory.form(Page.class).bindFromRequest();
        if(pageForm.hasErrors()) {
            return badRequest(pageForm.errorsAsJson());
        }

        Transaction txn = Ebean.beginTransaction();
        try {
            Page savedPage = Page.find.byId(id);
            if (savedPage != null) {
                Page newPageData = pageForm.get();
                savedPage.chapter = newPageData.chapter;
                savedPage.pageNumber = newPageData.pageNumber;
                savedPage.pageContent = newPageData.pageContent;

                savedPage.update();
                txn.commit();
            }
        } finally {
            txn.end();
        }

        return ok();
    }

    public Result delete(Long id) {
        Page.find.ref(id).delete();
        return ok();
    }
}
