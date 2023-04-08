# SreachTest
实现Android端的搜索框功能，内容如下
搜索界面，包括搜索框与历史记录的显示；搜索内容可以自动补全；点击历史记录可直接获取搜索结果。
思路介绍：
UI 思路： 自动补全采用 AutoCompleteTextView 实现，提示补全的下拉框为了美观，写了一个 invisibile的view，让下拉框在该 view 的下方出现；历史记录的排列为放得下就放，放不下就另外起一行，采用 Flexbox 与 RecyclerView 实现。
逻辑思路： 依据搜索框内容，点击搜索时，要完成以下四件事情：

1.）将搜索记录加入下方 RecyclerView 的适配器数据集合并刷新 RecyclerView；
2.）将搜索记录加入 AutoCompleteTextView 的适配器数据集合当中；
3.）把搜索记录保存到数据库；
4.）将搜索内容传入新活动中，完成搜索功能。点击历史记录进行搜索时，仅需要将搜索内容传入新活动中，完成搜索功能。点击清空图标时，需完成以下两件事情：（1）弹窗，提示用户是否清除历史记录；（2）若用户选择否，则 Toast 提示取消操作；若用户选择是则清空 RecyclerView 的适配器数据集合并刷新 RecyclerView，清空 AutoCompleteTextView 的适配器数据集合，Toast 提示完成操作。
值得注意的是重复搜索的数据需要做筛选，不做二次保存。
![image](https://img-blog.csdnimg.cn/20200508145935986.gif#pic_center)
