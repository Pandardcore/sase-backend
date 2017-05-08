package controllers;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Transaction;
import models.Chapter;
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
public class ChapterController extends Controller {

    private FormFactory formFactory;

    @Inject
    public ChapterController(FormFactory formFactory) {
        this.formFactory = formFactory;
    }

    public Result bookChapters(Long bookId) {
        List<Chapter> chapters = Chapter.find.where()
                .eq("book.id", bookId)
                .order().asc("chapNumber")
                .findList();
        if(chapters != null)  {
            return ok(Json.toJson(chapters));
        }
        return notFound();
    }

    public Result chapterById(Long id) {
        Chapter requestedChapter = Chapter.find.byId(id);
        if(requestedChapter != null) {
            return ok(Json.toJson(requestedChapter));
        }
        return notFound();
    }

    public Result bookChapterByNumber(Long bookId, Integer chapNumber) {
        Chapter chapter = Chapter.find.where()
                .eq("chapNumber", chapNumber)
                .eq("book.id", bookId)
                .findUnique();
        if(chapter != null) {
            return ok(Json.toJson(chapter));
        }
        return notFound();
    }

    public Result create() {
        Form<Chapter> chapterForm = formFactory.form(Chapter.class).bindFromRequest();
        if(chapterForm.hasErrors()) {
            return badRequest(chapterForm.errorsAsJson());
        }
        chapterForm.get().save();
        return ok();
    }

    public Result update(Long id) {
        Form<Chapter> chapterForm = formFactory.form(Chapter.class).bindFromRequest();
        if(chapterForm.hasErrors()) {
            return badRequest(chapterForm.errorsAsJson());
        }

        Transaction txn = Ebean.beginTransaction();
        try {
            Chapter savedChapter = Chapter.find.byId(id);
            if (savedChapter != null) {
                Chapter newChapterData = chapterForm.get();
                savedChapter.book = newChapterData.book;
                savedChapter.title = newChapterData.title;
                savedChapter.chapNumber = newChapterData.chapNumber;

                savedChapter.update();
                txn.commit();
            }
        } finally {
            txn.end();
        }

        return ok();
    }

    public Result delete(Long id) {
        Chapter.find.ref(id).delete();
        return ok();
    }
}
