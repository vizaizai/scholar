package com.github.vizaizai.scholar.infrastructure.util.tree;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author liaochongwei
 * @date 2020/9/14 10:31
 */
public class TreeUtils {
    private TreeUtils() {
    }


    public static <T extends TreeNode<I>, I> List<T> listToTree(List<T> nodes, I rootId) {
        List<T> treeNodes = new ArrayList<>();
        List<T> tempNodes = new ArrayList<>(nodes);
        for (T node : nodes) {
            if (rootId.equals(node.getParentId())) {
                // 添加父节点列表
                treeNodes.add(node);
            }
            node.setChildren(new ArrayList<>());
            // 给每一个节点寻找子节点
            Iterator<T> it = tempNodes.iterator();
            while (it.hasNext()) {
                T temp = it.next();
                if (node.getId().equals(temp.getParentId())) {
                    node.addChild(temp);
                    it.remove();
                }
            }
        }
        return treeNodes;
    }


    public static void main(String[] args) {

        List<AreaTreeNode> nodes = new ArrayList<>();
        nodes.add(new AreaTreeNode(1, 0, "贵州省"));
        nodes.add(new AreaTreeNode(2, 0, "四川省"));
        nodes.add(new AreaTreeNode(3, 1, "贵阳市"));
        nodes.add(new AreaTreeNode(4, 2, "成都市"));
        nodes.add(new AreaTreeNode(5, 3, "观山湖区"));
        nodes.add(new AreaTreeNode(6, 3, "花溪区"));
        nodes.add(new AreaTreeNode(7, 2, "绵阳市"));
        nodes.add(new AreaTreeNode(8, 4, "武侯区"));

        List<AreaTreeNode> treeNodes = listToTree(nodes, 0);
        System.out.println(JSON.toJSONString(treeNodes));
    }

    public static class AreaTreeNode extends TreeNode<Integer> {
        private String name;

        public AreaTreeNode() {
        }

        public AreaTreeNode(Integer id, Integer parentId, String name) {
            super(id, parentId);
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
