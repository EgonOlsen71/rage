package de.sixtyfour.rage;

import java.io.IOException;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.ServletException;

import com.sixtyfour.templating.Template;
import com.sixtyfour.templating.TemplateManager;

import de.skaliant.wax.core.ViewEngine;
import de.skaliant.wax.core.model.Call;
import de.skaliant.wax.core.model.ViewEngineConfig;

/**
 * A view engine for views written in BASIC V2 or 6502 assembly language.
 * 
 * @author EgonOlsen
 *
 */
public class CbmViewEngine implements ViewEngine {
	private ViewEngineConfig info = new ViewEngineConfig();

	public CbmViewEngine() {
		info.setLocation("/WEB-INF/views/");
		info.setSuffix(".cbm");
	}

	public ViewEngineConfig getConfig() {
		return info;
	}

	public void render(String view, Call call) throws ServletException, IOException {
		StringBuilder sb = new StringBuilder();
		String path = null;
		String viewLocation = info.getLocation();
		String viewSuffix = info.getSuffix();

		if (!view.startsWith("/")) {
			sb.append(viewLocation);
		}
		sb.append(view);
		if (!view.endsWith(".cbm")) {
			sb.append(viewSuffix);
		}
		path = sb.toString();

		path = call.getRealPath(path);
		Template tmpl = TemplateManager.getInstance().getTemplate(path);
		Map<String, Object> vars = call.getRequestScope().getNames().stream()
				.collect(Collectors.toMap(Function.identity(), x -> call.getRequestScope().get(x)));
		vars.putAll(call.getSessionScope().getNames().stream()
				.collect(Collectors.toMap(Function.identity(), x -> call.getSessionScope().get(x))));
		tmpl.setVariablesWithType(vars);

		String res = tmpl.process();
		call.getResponse().getWriter().println(res);
	}
}
