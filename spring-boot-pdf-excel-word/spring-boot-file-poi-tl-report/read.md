## SpringBoot + Poi-tl操作word，快速生成报表
##1、Poi-tl简介
poi-tl是一个免费开源的Java类库，是基于Apache POI的模板引擎，纯Java组件，跨平台，代码短小精悍，通过插件机制使其具有高度扩展性。

因此在使用的时候需要实现设置好模板，就像Freemarker一样，但是比其简单，也易操作。

##常用标签
格式：{{var}}
数据模型：
类型：                             描述：
    String                           纯文本
    TextRenderData                   带有样式的文本
    HyperLinkTextRenderData          超链接文本
    Object                           调用toString()方法转化成文本
代码:
put("author", new TextRenderData("000000", "Sayi"));
// 超链接
put("link",new HyperLinkTextRenderData("website", "http://deepoove.com"));

##2.2 图片
格式：以@开始，{{@var}}
格式：以@开始，{{@var}}

数据模型：
类型:                                    描述:
    PictureRenderData                       可以支持本地图片、也可以支持远程的URL地址（主要讲URL图片转成BufferedImage）

代码:
本地图片
put("local", new PictureRenderData(80, 100, "./sayi.png"));

// 图片流
put("localbyte", new PictureRenderData(80, 100, ".png", new FileInputStream("./logo.png")));

// 网络图片(注意网络耗时对系统可能的性能影响)
put("urlpicture", new PictureRenderData(50, 50, ".png", BytePictureUtils.getUrlBufferedImage("http://deepoove.com/images/icecream.png")));

##2.3 表格
poi-tl默认实现了N行N列的样式(如下图)，同时提供了当数据为空时，展示一行空数据的文案。

格式：以#开头，{{#var}}

数据模型：
类型:                              描述:
    MiniTableRenderData                 该类主要也是调用TextRenderData，提供样式等
    
##2.4列表
格式：以*开头，{{*var}}

数据模型：
类型:                          描述:
    NumbericRenderData          该类主要也是调用TextRenderData，提供样式等
NumbericRenderData中支持列表样式，主要有罗马字符、有序无序等。
FMT_DECIMAL //1. 2. 3.

FMT_DECIMAL_PARENTHESES //1) 2) 3)

FMT_BULLET //● ● ●

FMT_LOWER_LETTER //a. b. c.

FMT_LOWER_ROMAN //i ⅱ ⅲ

FMT_UPPER_LETTER //A. B. C.

##2.5 单系列图表
单系列图标，是指在图形中只展示一列数据，例如：单数据的柱状图，饼图等。
格式：先创建单系列图，然后在图表区格式 ->可选文字->标题。与文字一样，以{{val}}
数据模型：
类型:                                     描述:
    ChartSingleSeriesRenderData                 该类提供了设置标题、类别等
代码
ChartSingleSeriesRenderData singleSeriesRenderData = new ChartSingleSeriesRenderData();
singleSeriesRenderData.setCategories(new String[] { "俄罗斯", "加拿大", "美国", "中国" }");
singleSeriesRenderData.setChartTitle("测试");
pie.setSeriesData(new SeriesRenderData("countries", new Integer[] { 17098242, 9984670, 9826675, 9596961 }));

##2.6 多系列图表
在报表应用中，很多时候使用的是多系列组合
格式：与单系列一致
数据模型：
类型:                              描述:
    ChartMultiSeriesRenderData          该类提供了设置标题、类别等
代码
ChartMultiSeriesRenderData chart = new ChartMultiSeriesRenderData();
chart.setChartTitle("MyChart");
chart.setCategories(new String[] { "中文", "English" });
List<SeriesRenderData> seriesRenderData = new ArrayList<>();
seriesRenderData.add(new SeriesRenderData("countries", new Double[] { 15.0, 6.0 }));
seriesRenderData.add(new SeriesRenderData("speakers", new Double[] { 223.0, 119.0 }));
chart.setSeriesDatas(seriesRenderData);


