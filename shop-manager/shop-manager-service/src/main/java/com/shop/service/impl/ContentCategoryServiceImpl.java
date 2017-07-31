package com.shop.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.mapper.TbContentCategoryMapper;
import com.shop.pojo.EUTreeNode;
import com.shop.pojo.ShopResult;
import com.shop.pojo.TbContentCategory;
import com.shop.pojo.TbContentCategoryExample;
import com.shop.pojo.TbContentCategoryExample.Criteria;
import com.shop.service.ContentCategoryService;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;

	@Override
	public List<EUTreeNode> getCategoryList(long parentId) {
		// 根据id查询子结点
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria c = example.createCriteria();
		c.andParentIdEqualTo(parentId);
		List<EUTreeNode> resultList = new ArrayList<>();
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		// 返回一个node列表
		for (TbContentCategory tbContentCategory : list) {
			EUTreeNode node = new EUTreeNode(tbContentCategory.getId(), tbContentCategory.getName(),
					tbContentCategory.getIsParent() ? "closed" : "open");
			resultList.add(node);
		}
		return resultList;
	}

	// 新增内容分类结点
	@Override
	public ShopResult insertContentCategory(long parentId, String name) {
		// 创建一个pojo
		TbContentCategory tbContentCategory = new TbContentCategory();
		// id在数据库中是自增长的，在mapper中是使用主键返回的select last_insert_ID()
		tbContentCategory.setName(name);
		tbContentCategory.setIsParent(false);
		// 状态值，1.正常,2.删除
		tbContentCategory.setStatus(1);
		tbContentCategory.setSortOrder(1);
		tbContentCategory.setParentId(parentId);
		tbContentCategory.setCreated(new Date());
		tbContentCategory.setUpdated(new Date());
		// 插入数据库
		contentCategoryMapper.insert(tbContentCategory);
		// 更新其父节点
		// 查询父节点的isParent是否为true，如果不是，现在新添了叶节点，现在要设置成父节点
		TbContentCategory catParent = contentCategoryMapper.selectByPrimaryKey(parentId);
		if (!catParent.getIsParent()) {
			catParent.setIsParent(true);
			// 更新父节点的主键
			contentCategoryMapper.updateByPrimaryKey(catParent);
		}

		return ShopResult.ok(tbContentCategory);
	}

	// 删除一个节点
	@Override
	public ShopResult deleteContentCategory(long id) {

		TbContentCategory catNode = contentCategoryMapper.selectByPrimaryKey(id);
		long parentId = catNode.getParentId();

		// 判断被删节点的父节点是否还有子节点，根据主键查询
		TbContentCategory catParent = contentCategoryMapper.selectByPrimaryKey(parentId);

		// 删除
		contentCategoryMapper.deleteByPrimaryKey(id);

		// 查询子节点
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria c2 = example.createCriteria();
		c2.andParentIdEqualTo(parentId);
		List<TbContentCategory> childs = contentCategoryMapper.selectByExample(example);

		// 如果没有子节点，则将其状态设为false
		if (childs == null || childs.size() == 0) {
			catParent.setIsParent(false);
			// 更新父节点的主键
			contentCategoryMapper.updateByPrimaryKey(catParent);
		}

		return ShopResult.ok();
	}

	// 重命名节点的名字
	@Override
	public ShopResult updateContentCategory(long id, String name) {
		// 创建新的pojo
		TbContentCategory tbContentCategory =contentCategoryMapper.selectByPrimaryKey(id);
		tbContentCategory.setName(name);
		contentCategoryMapper.updateByPrimaryKey(tbContentCategory);
		return ShopResult.ok(tbContentCategory);
	}

}
