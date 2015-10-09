package com.cicada.service.impl.dish;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.util.NodeList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.cicada.core.model.dish.Dish;
import com.cicada.dao.dish.DishDao;
import com.cicada.service.api.dish.DishService;
import com.cicada.service.exception.RuntimeServiceException;
import com.cicada.service.exception.ServiceException;
import com.cicada.utils.HttpRequestUtils;
import com.cicada.utils.SecurityUtils;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DishServiceImpl implements DishService {

	private static final Logger logger = LoggerFactory.getLogger(DishServiceImpl.class);
	Map<String, List<Dish>> dishMap = new HashMap<String, List<Dish>>();

	@Autowired
	private DishDao dishDao;

	// @Override
	// public Dish randomSearch(List<String> dishIds) {
	// boolean isBreakfast = false;
	// Calendar calendar = Calendar.getInstance();
	// if (calendar.get(Calendar.HOUR_OF_DAY) > 5 && calendar.get(Calendar.HOUR_OF_DAY) < 9) {
	// isBreakfast = true;
	// }
	// List<Dish> dishs = dishDao.selectIdsReverse(dishIds, isBreakfast);
	// Dish theDish = null;
	// if (CollectionUtils.isNotEmpty(dishs)) {
	// theDish = dishs.get(new Random().nextInt(dishs.size()));
	// }
	//
	// return theDish;
	// }

	@Override
	public Dish randomSearch(List<String> dishIds) {
		String url = "http://r.ele.me/cxxcqxl";
		List<Dish> dishs = null;
		if(dishMap.containsKey(url))
		{
			dishs = dishMap.get(url);
		}else
		{
			String str = HttpRequestUtils.sendGet(url, "", "utf-8");
			System.out.println(str);
			dishs  = extractText(str);
			dishMap.put(url, dishs);
		}
		
		Dish theDish = null;
		if (CollectionUtils.isNotEmpty(dishs)) {
			theDish = dishs.get(new Random().nextInt(dishs.size()));
		}

		return theDish;
	}

	public static void main(String[] args) throws Exception {
		String url = "http://r.ele.me/cxxcqxl";
		String str = HttpRequestUtils.sendGet(url, "", "utf-8");
		System.out.println(str);
		System.out.println(extractText(str));

	}

	/**
	 * 抽取纯文本信息
	 * 
	 * @param inputHtml
	 * @return
	 */
	public static List<Dish> extractText(String inputHtml) {
		Parser parser;
		try {
			parser = Parser.createParser(inputHtml, "utf-8");
			NodeFilter filter = new HasAttributeFilter("class", "rst-d-name food_name");
			// NodeFilter filterChild = new HasChildFilter(filterA);
			// NodeFilter filter = new XorFilter(filterID, filterChild);
			// NodeFilter filter = new StringFilter("rst-menu-img-list");
			NodeList nodes = parser.extractAllNodesThatMatch(filter);
			List<Dish> list = new ArrayList<Dish>();
			if (nodes != null) {
				for (int i = 0; i < nodes.size(); i++) {
					Node node = (Node) nodes.elementAt(i);

					Dish dish = new Dish();
					System.out.println(node.toPlainTextString());
					dish.setName(node.toPlainTextString());
					list.add(dish);
				}
			}

			return list;
		} catch (Exception e) {
			logger.error("error", e);
			throw new RuntimeException(e);
		}

	}

	@Override
	public void create(String names, boolean isBreakfast) throws ServiceException {
		try {
			Dish dish = null;
			if (StringUtils.isNotEmpty(names)) {
				String[] arrs = names.split("[、，。；？！,.;?!]");
				if (arrs.length > 0) {
					for (int i = 0; i < arrs.length; i++) {
						dish = new Dish();
						dish.setBreakfast(isBreakfast);
						dish.setName(arrs[i]);
						dish.setPhoto("empty");
						dish.setID(SecurityUtils.UUID());
						dish.setOwnerId("cheng");
						try {
							dishDao.insert(dish);
						} catch (DuplicateKeyException e) {
							logger.warn("duplicate name");
						}
					}
				}
			}
		} catch (RuntimeException e) {
			logger.error("runtime exception ", e);
			throw new RuntimeServiceException("runtime exception", e);
		}

	}

}
