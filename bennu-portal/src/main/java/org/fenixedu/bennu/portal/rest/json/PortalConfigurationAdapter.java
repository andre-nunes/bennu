package org.fenixedu.bennu.portal.rest.json;

import org.fenixedu.bennu.core.annotation.DefaultJsonAdapter;
import org.fenixedu.bennu.core.json.JsonBuilder;
import org.fenixedu.bennu.core.json.JsonUpdater;
import org.fenixedu.bennu.core.json.JsonViewer;
import org.fenixedu.bennu.core.json.adapters.DomainObjectViewer;
import org.fenixedu.bennu.core.util.CoreConfiguration;
import org.fenixedu.bennu.portal.domain.PortalConfiguration;
import org.fenixedu.bennu.portal.servlet.PortalInitializer;
import org.fenixedu.commons.i18n.LocalizedString;

import com.google.common.base.Strings;
import com.google.common.io.BaseEncoding;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

@DefaultJsonAdapter(PortalConfiguration.class)
public class PortalConfigurationAdapter implements JsonViewer<PortalConfiguration>, JsonUpdater<PortalConfiguration> {

    private static final boolean developmentMode = CoreConfiguration.getConfiguration().developmentMode();

    @Override
    public JsonElement view(PortalConfiguration configuration, JsonBuilder ctx) {
        JsonObject object = new JsonObject();
        object.addProperty("id", configuration.getExternalId());
        object.add("applicationTitle", configuration.getApplicationTitle().json());
        object.add("htmlTitle", configuration.getHtmlTitle().json());
        object.add("applicationSubTitle", configuration.getApplicationSubTitle().json());
        object.add("applicationCopyright", configuration.getApplicationCopyright().json());
        object.addProperty("developmentMode", developmentMode);
        object.addProperty("supportEmailAddress", configuration.getSupportEmailAddress());
        object.addProperty("systemEmailAddress", configuration.getSystemEmailAddress());
        object.addProperty("theme", configuration.getTheme());
        if (configuration.getLogo() != null) {
            object.addProperty("logo", BaseEncoding.base64().encode(configuration.getLogo()));
            object.addProperty("logoType", new String(configuration.getLogoType()));
            if (!Strings.isNullOrEmpty(configuration.getLogoLinkUrl())) {
                object.addProperty("logoLinkUrl", configuration.getLogoLinkUrl());
            }
            if (!Strings.isNullOrEmpty(configuration.getLogoTooltip())) {
                object.addProperty("logoTooltip", configuration.getLogoTooltip());
            }
        }
        if (configuration.getFavicon() != null) {
            object.addProperty("favicon", BaseEncoding.base64().encode(configuration.getFavicon()));
            object.addProperty("faviconType", new String(configuration.getLogoType()));
        }
        JsonArray themes = new JsonArray();
        for (String theme : PortalInitializer.getThemes()) {
            themes.add(new JsonPrimitive(theme));
        }
        object.add("themes", themes);
        object.add("menu", ctx.view(configuration.getMenu(), DomainObjectViewer.class));
        return object;
    }

    @Override
    public PortalConfiguration update(JsonElement json, PortalConfiguration configuration, JsonBuilder ctx) {
        JsonObject object = json.getAsJsonObject();
        if (object.has("applicationTitle")) {
            configuration.setApplicationTitle(LocalizedString.fromJson(object.get("applicationTitle")));
        }
        if (object.has("htmlTitle")) {
            configuration.setHtmlTitle(LocalizedString.fromJson(object.get("htmlTitle")));
        }
        if (object.has("applicationSubTitle")) {
            configuration.setApplicationSubTitle(LocalizedString.fromJson(object.get("applicationSubTitle")));
        }
        if (object.has("applicationCopyright")) {
            configuration.setApplicationCopyright(LocalizedString.fromJson(object.get("applicationCopyright")));
        }
        if (object.has("supportEmailAddress")) {
            configuration.setSupportEmailAddress(object.get("supportEmailAddress").getAsString());
        }
        if (object.has("systemEmailAddress")) {
            configuration.setSystemEmailAddress(object.get("systemEmailAddress").getAsString());
        }
        if (object.has("theme")) {
            configuration.setTheme(object.get("theme").getAsString());
        }
        if (object.has("logo")) {
            configuration.setLogo(BaseEncoding.base64().decode(object.get("logo").getAsString()));
        }
        if (object.has("logoType")) {
            configuration.setLogoType(object.get("logoType").getAsString());
        }
        if (object.has("logoLinkUrl")) {
            configuration.setLogoLinkUrl(object.get("logoLinkUrl").getAsString());
        }
        if (object.has("logoTooltip")) {
            configuration.setLogoTooltip(object.get("logoTooltip").getAsString());
        }
        if (object.has("favicon")) {
            configuration.setFavicon(BaseEncoding.base64().decode(object.get("favicon").getAsString()));
        }
        if (object.has("faviconType")) {
            configuration.setFaviconType(object.get("faviconType").getAsString());
        }
        return configuration;
    }
}
