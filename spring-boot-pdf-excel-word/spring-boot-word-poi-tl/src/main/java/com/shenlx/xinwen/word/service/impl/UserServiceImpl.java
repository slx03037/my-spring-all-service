package com.shenlx.xinwen.word.service.impl;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.*;
import com.deepoove.poi.plugin.markdown.MarkdownRenderData;
import com.deepoove.poi.plugin.markdown.MarkdownRenderPolicy;
import com.deepoove.poi.plugin.markdown.MarkdownStyle;
import com.shenlx.xinwen.word.model.entity.User;
import com.shenlx.xinwen.word.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-07-11 10:11
 **/
@Slf4j
@Service
public class UserServiceImpl implements IUserService {
    @Override
    public XWPFTemplate generateWordXWPFTemplate() throws IOException {
        Map<String, Object> content = new HashMap<>();
        content.put("title", "Java 全栈知识体系");
        content.put("author", "pdai");
        content.put("site", new HyperlinkTextRenderData("https://www.baidu", "https://www.baidu"));

        content.put("poiText", "Apache POI 是创建和维护操作各种符合Office Open XML（OOXML）标准和微软的OLE 2复合文档格式（OLE2）的Java API。用它可以使用Java读取和创建,修改MS Excel文件.而且,还可以使用Java读取和创建MS Word和MSPowerPoint文件。更多请参考[官方文档](https://poi.apache.org/index.html)");

        content.put("poiText2", "生成xls和xlsx有什么区别？POI对Excel中的对象的封装对应关系？");
        content.put("poiList", Numberings.create("excel03只能打开xls格式，无法直接打开xlsx格式",
                "xls只有65536行、256列; xlsx可以有1048576行、16384列",
                "xls占用空间大, xlsx占用空间小，运算速度也会快一点"));

        RowRenderData headRow = Rows.of("ID", "Name", "Email", "TEL", "Description").textColor("FFFFFF")
                .bgColor("4472C4").center().create();
        TableRenderData table = Tables.create(headRow);
        getUserList()
                .forEach(a -> table.addRow(Rows.create(a.getId() + "", a.getUserName(), a.getEmail(), a.getPhoneNumber() + "", a.getDescription())));
        content.put("poiTable", table);

        Resource resource = new ClassPathResource("1.jpeg");
        content.put("poiImage", Pictures.ofStream(new FileInputStream(resource.getFile())).create());

        return XWPFTemplate.compile(new ClassPathResource("xxx.docx").getFile()).render(content);
    }

    @Override
    public XWPFTemplate generateWordXWPFTemplateMD() throws IOException {
        MarkdownRenderData code = new MarkdownRenderData();

        Resource resource = new ClassPathResource("test.md");
        code.setMarkdown(new String(Files.readAllBytes(resource.getFile().toPath())));
        code.setStyle(MarkdownStyle.newStyle());

        Map<String, Object> data = new HashMap<>();
        data.put("md", code);

        Configure config = Configure.builder().bind("md", new MarkdownRenderPolicy()).build();

        return XWPFTemplate.compile(new ClassPathResource("markdown.docx").getFile(), config).render(data);
    }

    private List<User> getUserList() {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            userList.add(User.builder()
                    .id(Long.parseLong(i + "")).userName("xxx" + i).email("https://www.baidu" + i).phoneNumber(121231231231L)
                    .description("hello world" + i)
                    .build());
        }
        return userList;
    }

}
