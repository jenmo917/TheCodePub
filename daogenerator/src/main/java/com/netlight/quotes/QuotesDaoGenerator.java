package com.netlight.quotes;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class QuotesDaoGenerator {

    private static final int VERSION_NUMBER = 1;

    /**
     * REMEMBER TO INCREASE VERSION NUMBER WHEN CHANGING THE DATABASE
     */
    public static void main(String[] args) throws Exception {

        Schema schema = new Schema(VERSION_NUMBER,
                "com.netlight.quotes.app.model.db");
        schema.setDefaultJavaPackageDao("com.netlight.quotes.app.model.dao");
        schema.enableKeepSectionsByDefault();
        generateEntities(schema);
        new DaoGenerator().generateAll(schema,
                "../app/src/main/java/");
    }

    private static void generateEntities(Schema schema) {
        Entity newEpisode = schema.addEntity("Quote");
        newEpisode.implementsInterface("Parcelable");
        newEpisode.addIdProperty();
        newEpisode.addStringProperty("quote").notNull();
        newEpisode.addStringProperty("author").notNull();
        newEpisode.addStringProperty("category").notNull();
    }
}