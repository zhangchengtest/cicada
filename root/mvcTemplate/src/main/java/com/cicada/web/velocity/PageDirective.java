package com.cicada.web.velocity;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.exception.TemplateInitException;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;
import org.apache.velocity.runtime.parser.node.SimpleNode;

import com.cicada.core.model.page.Pagination;
import com.cicada.web.spring.SpringResourceLoader;

/**
 * 分页标签
 * @author zhangcheng
 *
 */
public class PageDirective extends Directive {

	private static final VelocityEngine engine = new VelocityEngine();
	/**
	 * 分页模板
	 */
	private static final Hashtable<String, String> templates = new Hashtable<String, String>();

	private static final String PAGE_STYLE_PATH = "/page/";
	private static final String DEFAULT_STYLE_NAME = "default";

	public PageDirective() {
	}

	public String getName() {
		return "page";
	}

	public int getType() {
		return 2;
	}

	public void init(RuntimeServices rs, InternalContextAdapter context, Node node) throws TemplateInitException {
		super.init(rs, context, node);
		try {
			loadTemplates(SpringResourceLoader.getResource(PAGE_STYLE_PATH).getFile().getAbsolutePath().toString(), rs
					.getProperty("input.encoding").toString());
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public boolean render(InternalContextAdapter context, Writer writer, Node node) throws IOException,
			ResourceNotFoundException, ParseErrorException, MethodInvocationException {
		int offset = 10;
		String style = DEFAULT_STYLE_NAME;
		int paramnum = node.jjtGetNumChildren();
		if (paramnum < 2)
			return false;
		SimpleNode snPageset = (SimpleNode) node.jjtGetChild(0);
		Pagination<?> pageset = (Pagination<?>) snPageset.value(context);
		if (pageset == null)
			return false;
		SimpleNode snURL = (SimpleNode) node.jjtGetChild(1);
		String url = (String) snURL.value(context);
		if (paramnum > 2) {
			SimpleNode snOffset = (SimpleNode) node.jjtGetChild(2);
			offset = ((Integer) snOffset.value(context)).intValue();
		}
		if (paramnum > 3) {
			SimpleNode snStyle = (SimpleNode) node.jjtGetChild(3);
			String _style = (String) snStyle.value(context);
			if (templates.containsKey(_style))
				style = _style;
		}
		Map<String, Object> cxt = new HashMap<String, Object>();
		cxt.put("url", url);
		cxt.put("totalrows", Integer.valueOf(pageset.getTotalRows()));
		cxt.put("pagesize", Integer.valueOf(pageset.getPageSize()));
		cxt.put("currpagenum", Integer.valueOf(pageset.getCurrPageNum()));
		cxt.put("totalpages", Integer.valueOf(pageset.getTotalPages()));
		cxt.put("offsetpagenums", getOffsetPageNums(pageset, offset));
		String vm = (String) templates.get(style);
		writer.write(renderTemplate(cxt, vm));
		return true;
	}

	private static String renderTemplate(Map<String, Object> cxt, String vm) {
		StringWriter writer = new StringWriter();
		VelocityContext context = new VelocityContext(cxt);
		try {
			engine.evaluate(context, writer, "", vm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return writer.toString();
	}

	/**
	 * 获取页面显示具体页数 如传入3 页面则显示三个页数 如何 上一页 [1] 2 [3] 下一页
	 * 
	 * @param pageset
	 * @param offset
	 *            页面显示页数
	 * @return
	 */
	private static int[] getOffsetPageNums(Pagination<?> pageset, int offset) {
		int startOffsetPageNum = pageset.getCurrPageNum();
		int endOffsetPageNum = pageset.getCurrPageNum();
		do {
			if (startOffsetPageNum > 1) {
				offset--;
				startOffsetPageNum--;
			}
			if (offset > 1 && endOffsetPageNum < pageset.getTotalPages()) {
				offset--;
				endOffsetPageNum++;
			}
		} while ((startOffsetPageNum > 1 || endOffsetPageNum < pageset.getTotalPages()) && offset > 1);
		int offsetPageNums[] = new int[(endOffsetPageNum - startOffsetPageNum) + 1];
		int i = 0;
		for (int num = startOffsetPageNum; num <= endOffsetPageNum; num++) {
			offsetPageNums[i] = num;
			i++;
		}

		return offsetPageNums;
	}

	private static void loadTemplates(String path, String charset) {
		File folder = new File(path);
		File afile[];
		int j = (afile = folder.listFiles()).length;
		for (int i = 0; i < j; i++) {
			File file = afile[i];
			if (file.isFile() && file.getName().endsWith(".vm")) {
				String fileText;
				try {
					fileText = FileUtils.readFileToString(file, charset);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
				templates.put(replaceIgnoreCase(file.getName(), ".vm", ""), fileText);
			}
		}

	}

	private static String replaceIgnoreCase(String string, String oldString, String newString) {
		if (string == null)
			return "";
		String lcString = string.toLowerCase();
		String lcOldString = oldString.toLowerCase();
		int i = 0;
		if ((i = lcString.indexOf(lcOldString, i)) >= 0) {
			char string2[] = string.toCharArray();
			char newString2[] = newString.toCharArray();
			StringBuilder buf = new StringBuilder(string2.length);
			buf.append(string2, 0, i).append(newString2);
			int oldStrLength = oldString.length();
			i += oldStrLength;
			int j;
			for (j = i; (i = lcString.indexOf(lcOldString, i)) > 0; j = i) {
				buf.append(string2, j, i - j).append(newString2);
				i += oldStrLength;
			}

			return buf.append(string2, j, string2.length - j).toString();
		} else {
			return string;
		}
	}

}
