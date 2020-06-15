package jedisDemo;

import com.metamug.entity.Attachment;
import com.metamug.entity.Request;
import com.metamug.entity.Response;
import com.metamug.exec.RequestProcessable;

import redis.clients.jedis.JedisPool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import javax.sql.DataSource;

public class FileDownload implements RequestProcessable {
	public Response process(Request request, DataSource ds, Map<String, Object> args) throws Exception {

        JedisMain jedis = new JedisMain((String) args.get("host"),
                (int) args.get("port"));
        InputStream is = jedis.download(request.getParameter("file"));
        Attachment attachment = new Attachment(is);
        attachment.setName(request.getParameter("file"));
        Response response = new Response(attachment);
//            Response response = new Response(is);
        return response;

    }
	
	private static void copyInputStreamToFile(InputStream inputStream, File file)
            throws IOException {

        try (FileOutputStream outputStream = new FileOutputStream(file)) {

            int read;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        }

    }
}
