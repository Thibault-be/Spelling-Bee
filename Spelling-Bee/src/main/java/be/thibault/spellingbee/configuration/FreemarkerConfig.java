package be.thibault.spellingbee.configuration;


import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.TimeZone;

@Component
public class FreemarkerConfig {


    public Configuration freemarkerConfiguration() {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_34);

        try {
            configuration.setDirectoryForTemplateLoading(new File("D:/Projects/Spelling Bee/Spelling-Bee/src/main/resources/templates"));
            configuration.setDefaultEncoding("UTF-8");
            configuration.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
            configuration.setLogTemplateExceptions(false);
            configuration.setWrapUncheckedExceptions(true);
            configuration.setFallbackOnNullLoopVariable(false);
            configuration.setSQLDateAndTimeTimeZone(TimeZone.getDefault());
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
        return configuration;
    }
}
