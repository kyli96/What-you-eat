package com.lishouse.foodjournal.web;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.google.sitebricks.At;
import com.google.sitebricks.headless.Reply;
import com.google.sitebricks.headless.Service;
import com.google.sitebricks.http.Post;
import com.google.sitebricks.http.Get;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.io.IOUtils;

import java.util.UUID;
import java.io.IOException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lishouse.foodjournal.data.Entry;
import com.lishouse.foodjournal.data.Picture;
import com.lishouse.foodjournal.data.User;
import com.lishouse.foodjournal.data.EntryStore;

@At("/ws/picture") @Service
public class PictureWS {
    @Inject
    private EntryStore entryStore;

    @Inject
    private CurrentUser currentUser;

    @Post
    Reply<?> uploadPicture(HttpServletRequest req) throws IOException, FileUploadException {
        User user = currentUser.getUser();
        if (null == user) {
            return Reply.with("{success:'false',message:'user not logged in'}");
        }

        String fileName = req.getParameter("qqfile");

        ServletInputStream inputStream = req.getInputStream();
        byte[] data = IOUtils.toByteArray(inputStream);
        IOUtils.closeQuietly(inputStream);

        Picture pic = new Picture();
        pic.setId(UUID.randomUUID().getMostSignificantBits());
        pic.setUser(user);
        pic.setName(fileName);
        pic.setContent(new Blob(data));
        pic.setMimeType(findMimeType(fileName));

        entryStore.save(pic);

        return Reply.with("{success:'true',id:'" + pic.getId() + "'}");
    }

    Reply<?> getPicture(String id, int width, int height, HttpServletResponse resp) throws IOException {
        Long picId = Long.valueOf(id);

        Picture pic = entryStore.fetchPicture(picId);
        if (null == pic) {
            return Reply.saying().notFound();
        }

        ImagesService imagesService = ImagesServiceFactory.getImagesService();

        byte[] bytes = pic.getContent().getBytes();
        Image orig = ImagesServiceFactory.makeImage(bytes);
        Transform resize = ImagesServiceFactory.makeResize(width,height);
        Image img = imagesService.applyTransform(resize,orig);
        byte[] bytes_out = img.getImageData();

        resp.setContentType(pic.getMimeType());
        resp.setContentLength(bytes_out.length);
        ServletOutputStream out = resp.getOutputStream();
        IOUtils.write(bytes_out, out);
        IOUtils.closeQuietly(out);

        return Reply.saying().ok();
    }

    @At("/:id") @Get
    Reply<?> getPicture(@Named("id") String id, HttpServletResponse resp) throws IOException {
        return getPicture(id,250,250,resp);
    }

    @At("/:id/:type") @Get
    Reply<?> getPicture(@Named("id") String id, @Named("type") String type, HttpServletResponse resp) throws IOException {
        if (type.equals("thumb")) {
            return getPicture(id,50,50,resp);
        } else {
            return getPicture(id,250,250,resp);
        }
    }

    private String findMimeType(String fileName) {
        if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (fileName.endsWith(".gif")) {
            return "image/gif";
        } else if (fileName.endsWith(".png")) {
            return "image/png";
        }
        // Unknown.
        return "application/octet-stream";
    }

}
