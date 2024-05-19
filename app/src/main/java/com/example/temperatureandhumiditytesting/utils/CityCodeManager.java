package com.example.temperatureandhumiditytesting.utils;



import com.alibaba.fastjson.JSON;
import com.example.temperatureandhumiditytesting.bean.Tb_CityCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CityCodeManager {
    public static  HashMap<String, Integer> cityCodeMap;
    private static List<Tb_CityCode> tbCityCodeList;
    private static  Tb_CityCode tb_cityCode;
    private static  String cityCode =  "[\n" +
            "{\"cityName\":\"北京\",\"cityCode\":101010100},\n" +
            "{\"cityName\":\"天津\",\"cityCode\":101030100},\n" +

            "{\"cityName\":\"上海\",\"cityCode\":101020100},\n" +

            "{\"cityName\":\"石家庄\",\"cityCode\":101090101},\n" +
            "{\"cityName\":\"张家口\",\"cityCode\":101090301},\n" +
            "{\"cityName\":\"承德\",\"cityCode\":101090402},\n" +
            "{\"cityName\":\"唐山\",\"cityCode\":101090501},\n" +
            "{\"cityName\":\"秦皇岛\",\"cityCode\":101091101},\n" +
            "{\"cityName\":\"沧州\",\"cityCode\":101090701},\n" +
            "{\"cityName\":\"衡水\",\"cityCode\":101090801},\n" +
            "{\"cityName\":\"邢台\",\"cityCode\":101090901},\n" +
            "{\"cityName\":\"邯郸\",\"cityCode\":101091001},\n" +
            "{\"cityName\":\"保定\",\"cityCode\":101090201},\n" +
            "{\"cityName\":\"廊坊\",\"cityCode\":101090601},\n" +
            "{\"cityName\":\"郑州\",\"cityCode\":101180101},\n" +

            "{\"cityName\":\"开封\",\"cityCode\":101180801},\n" +
            "{\"cityName\":\"安阳\",\"cityCode\":101180201},\n" +
            "{\"cityName\":\"合肥\",\"cityCode\":101220101},\n" +
            "{\"cityName\":\"芜湖\",\"cityCode\":101220301},\n" +
            "{\"cityName\":\"淮南\",\"cityCode\":101220401},\n" +
            "{\"cityName\":\"马鞍山\",\"cityCode\":101220501},\n" +

            "{\"cityName\":\"滁州\",\"cityCode\":101221101},\n" +

            "{\"cityName\":\"杭州\",\"cityCode\":101210101},\n" +
            "{\"cityName\":\"舟山\",\"cityCode\":101211101},\n" +
            "{\"cityName\":\"湖州\",\"cityCode\":101210201},\n" +
            "{\"cityName\":\"嘉兴\",\"cityCode\":101210301},\n" +
            "{\"cityName\":\"金华\",\"cityCode\":101210901},\n" +
            "{\"cityName\":\"绍兴\",\"cityCode\":101210501},\n" +
            "{\"cityName\":\"台州\",\"cityCode\":101210601},\n" +
            "{\"cityName\":\"温州\",\"cityCode\":101210701},\n" +
            "{\"cityName\":\"丽水\",\"cityCode\":101210801},\n" +
            "{\"cityName\":\"衢州\",\"cityCode\":101211001},\n" +
            "{\"cityName\":\"宁波\",\"cityCode\":101210401},\n" +
            "{\"cityName\":\"重庆\",\"cityCode\":101040100},\n" +
            "{\"cityName\":\"合川\",\"cityCode\":101040300},\n" +
            "{\"cityName\":\"南川\",\"cityCode\":101040400},\n" +
            "{\"cityName\":\"江津\",\"cityCode\":101040500},\n" +
            "{\"cityName\":\"万盛\",\"cityCode\":101040600},\n" +
            "{\"cityName\":\"渝北\",\"cityCode\":101040700},\n" +
            "{\"cityName\":\"北碚\",\"cityCode\":101040800},\n" +
            "{\"cityName\":\"巴南\",\"cityCode\":101040900},\n" +
            "{\"cityName\":\"长寿\",\"cityCode\":101041000},\n" +
            "{\"cityName\":\"黔江\",\"cityCode\":101041100},\n" +
            "{\"cityName\":\"万州天城\",\"cityCode\":101041200},\n" +
            "{\"cityName\":\"万州龙宝\",\"cityCode\":101041300},\n" +
            "{\"cityName\":\"涪陵\",\"cityCode\":101041400},\n" +
            "{\"cityName\":\"开县\",\"cityCode\":101041500},\n" +
            "{\"cityName\":\"城口\",\"cityCode\":101041600},\n" +
            "{\"cityName\":\"云阳\",\"cityCode\":101041700},\n" +
            "{\"cityName\":\"巫溪\",\"cityCode\":101041800},\n" +
            "{\"cityName\":\"奉节\",\"cityCode\":101041900},\n" +
            "{\"cityName\":\"巫山\",\"cityCode\":101042000},\n" +
            "{\"cityName\":\"潼南\",\"cityCode\":101042100},\n" +
            "{\"cityName\":\"垫江\",\"cityCode\":101042200},\n" +
            "{\"cityName\":\"梁平\",\"cityCode\":101042300},\n" +
            "{\"cityName\":\"忠县\",\"cityCode\":101042400},\n" +
            "{\"cityName\":\"石柱\",\"cityCode\":101042500},\n" +
            "{\"cityName\":\"大足\",\"cityCode\":101042600},\n" +
            "{\"cityName\":\"荣昌\",\"cityCode\":101042700},\n" +
            "{\"cityName\":\"铜梁\",\"cityCode\":101042800},\n" +
            "{\"cityName\":\"璧山\",\"cityCode\":101042900},\n" +
            "{\"cityName\":\"丰都\",\"cityCode\":101043000},\n" +
            "{\"cityName\":\"武隆\",\"cityCode\":101043100},\n" +
            "{\"cityName\":\"彭水\",\"cityCode\":101043200},\n" +
            "{\"cityName\":\"綦江\",\"cityCode\":101043300},\n" +
            "{\"cityName\":\"酉阳\",\"cityCode\":101043400},\n" +
            "{\"cityName\":\"秀山\",\"cityCode\":101043600},\n" +
            "{\"cityName\":\"沙坪坝\",\"cityCode\":101043700},\n" +
            "{\"cityName\":\"永川\",\"cityCode\":101040200},\n" +
            "{\"cityName\":\"福州\",\"cityCode\":101230101},\n" +
            "{\"cityName\":\"泉州\",\"cityCode\":101230501},\n" +
            "{\"cityName\":\"漳州\",\"cityCode\":101230601},\n" +
            "{\"cityName\":\"龙岩\",\"cityCode\":101230701},\n" +
            "{\"cityName\":\"晋江\",\"cityCode\":101230509},\n" +
            "{\"cityName\":\"南平\",\"cityCode\":101230901},\n" +
            "{\"cityName\":\"厦门\",\"cityCode\":101230201},\n" +
            "{\"cityName\":\"宁德\",\"cityCode\":101230301},\n" +
            "{\"cityName\":\"莆田\",\"cityCode\":101230401},\n" +
            "{\"cityName\":\"三明\",\"cityCode\":101230801},\n" +
            "{\"cityName\":\"兰州\",\"cityCode\":101160101},\n" +
            "{\"cityName\":\"平凉\",\"cityCode\":101160301},\n" +
            "{\"cityName\":\"庆阳\",\"cityCode\":101160401},\n" +
            "{\"cityName\":\"武威\",\"cityCode\":101160501},\n" +
            "{\"cityName\":\"金昌\",\"cityCode\":101160601},\n" +
            "{\"cityName\":\"嘉峪关\",\"cityCode\":101161401},\n" +
            "{\"cityName\":\"酒泉\",\"cityCode\":101160801},\n" +
            "{\"cityName\":\"天水\",\"cityCode\":101160901},\n" +
            "{\"cityName\":\"武都\",\"cityCode\":101161001},\n" +
            "{\"cityName\":\"临夏\",\"cityCode\":101161101},\n" +
            "{\"cityName\":\"合作\",\"cityCode\":101161201},\n" +
            "{\"cityName\":\"白银\",\"cityCode\":101161301},\n" +
            "{\"cityName\":\"定西\",\"cityCode\":101160201},\n" +
            "{\"cityName\":\"张掖\",\"cityCode\":101160701},\n" +
            "{\"cityName\":\"广州\",\"cityCode\":101280101},\n" +
            "{\"cityName\":\"惠州\",\"cityCode\":101280301},\n" +
            "{\"cityName\":\"梅州\",\"cityCode\":101280401},\n" +
            "{\"cityName\":\"汕头\",\"cityCode\":101280501},\n" +
            "{\"cityName\":\"深圳\",\"cityCode\":101280601},\n" +
            "{\"cityName\":\"珠海\",\"cityCode\":101280701},\n" +
            "{\"cityName\":\"佛山\",\"cityCode\":101280800},\n" +
            "{\"cityName\":\"肇庆\",\"cityCode\":101280901},\n" +
            "{\"cityName\":\"湛江\",\"cityCode\":101281001},\n" +
            "{\"cityName\":\"江门\",\"cityCode\":101281101},\n" +
            "{\"cityName\":\"河源\",\"cityCode\":101281201},\n" +
            "{\"cityName\":\"清远\",\"cityCode\":101281301},\n" +
            "{\"cityName\":\"云浮\",\"cityCode\":101281401},\n" +
            "{\"cityName\":\"潮州\",\"cityCode\":101281501},\n" +
            "{\"cityName\":\"东莞\",\"cityCode\":101281601},\n" +
            "{\"cityName\":\"中山\",\"cityCode\":101281701},\n" +

            "{\"cityName\":\"南宁\",\"cityCode\":101300101},\n" +
            "{\"cityName\":\"柳州\",\"cityCode\":101300301},\n" +
            "{\"cityName\":\"来宾\",\"cityCode\":101300401},\n" +
            "{\"cityName\":\"桂林\",\"cityCode\":101300501},\n" +
            "{\"cityName\":\"梧州\",\"cityCode\":101300601},\n" +

            "{\"cityName\":\"玉溪\",\"cityCode\":101290701},\n" +
            "{\"cityName\":\"楚雄\",\"cityCode\":101290801},\n" +
            "{\"cityName\":\"普洱\",\"cityCode\":101290901},\n" +
            "{\"cityName\":\"昭通\",\"cityCode\":101291001},\n" +
            "{\"cityName\":\"临沧\",\"cityCode\":101291101},\n" +
            "{\"cityName\":\"怒江\",\"cityCode\":101291201},\n" +

            "{\"cityName\":\"大理\",\"cityCode\":101290201},\n" +

            "{\"cityName\":\"呼和浩特\",\"cityCode\":101080101},\n" +
            "{\"cityName\":\"乌海\",\"cityCode\":101080301},\n" +
            "{\"cityName\":\"集宁\",\"cityCode\":101080401},\n" +
            "{\"cityName\":\"通辽\",\"cityCode\":101080501},\n" +
            "{\"cityName\":\"阿拉善左旗\",\"cityCode\":101081201},\n" +
            "{\"cityName\":\"鄂尔多斯\",\"cityCode\":101080701},\n" +
            "{\"cityName\":\"临河\",\"cityCode\":101080801},\n" +
            "{\"cityName\":\"锡林浩特\",\"cityCode\":101080901},\n" +
            "{\"cityName\":\"呼伦贝尔\",\"cityCode\":101081000},\n" +
            "{\"cityName\":\"乌兰浩特\",\"cityCode\":101081101},\n" +
            "{\"cityName\":\"包头\",\"cityCode\":101080201},\n" +
            "{\"cityName\":\"赤峰\",\"cityCode\":101080601},\n" +
            "{\"cityName\":\"南昌\",\"cityCode\":101240101},\n" +
            "{\"cityName\":\"上饶\",\"cityCode\":101240301},\n" +
            "{\"cityName\":\"抚州\",\"cityCode\":101240401},\n" +
            "{\"cityName\":\"宜春\",\"cityCode\":101240501},\n" +
            "{\"cityName\":\"鹰潭\",\"cityCode\":101241101},\n" +
            "{\"cityName\":\"赣州\",\"cityCode\":101240701},\n" +
            "{\"cityName\":\"景德镇\",\"cityCode\":101240801},\n" +
            "{\"cityName\":\"萍乡\",\"cityCode\":101240901},\n" +
            "{\"cityName\":\"新余\",\"cityCode\":101241001},\n" +
            "{\"cityName\":\"九江\",\"cityCode\":101240201},\n" +
            "{\"cityName\":\"吉安\",\"cityCode\":101240601},\n" +
            "{\"cityName\":\"武汉\",\"cityCode\":101200101},\n" +
            "{\"cityName\":\"黄冈\",\"cityCode\":101200501},\n" +
            "{\"cityName\":\"荆州\",\"cityCode\":101200801},\n" +
            "{\"cityName\":\"宜昌\",\"cityCode\":101200901},\n" +
            "{\"cityName\":\"恩施\",\"cityCode\":101201001},\n" +
            "{\"cityName\":\"十堰\",\"cityCode\":101201101},\n" +
            "{\"cityName\":\"神农架\",\"cityCode\":101201201},\n" +
            "{\"cityName\":\"随州\",\"cityCode\":101201301},\n" +
            "{\"cityName\":\"荆门\",\"cityCode\":101201401},\n" +
            "{\"cityName\":\"天门\",\"cityCode\":101201501},\n" +
            "{\"cityName\":\"仙桃\",\"cityCode\":101201601},\n" +
            "{\"cityName\":\"潜江\",\"cityCode\":101201701},\n" +
            "{\"cityName\":\"襄樊\",\"cityCode\":101200201},\n" +
            "{\"cityName\":\"鄂州\",\"cityCode\":101200301},\n" +
            "{\"cityName\":\"孝感\",\"cityCode\":101200401},\n" +
            "{\"cityName\":\"黄石\",\"cityCode\":101200601},\n" +
            "{\"cityName\":\"咸宁\",\"cityCode\":101200701},\n" +
            "{\"cityName\":\"成都\",\"cityCode\":101270101},\n" +
            "{\"cityName\":\"自贡\",\"cityCode\":101270301},\n" +
            "{\"cityName\":\"绵阳\",\"cityCode\":101270401},\n" +
            "{\"cityName\":\"南充\",\"cityCode\":101270501},\n" +
            "{\"cityName\":\"达州\",\"cityCode\":101270601},\n" +
            "{\"cityName\":\"遂宁\",\"cityCode\":101270701},\n" +
            "{\"cityName\":\"广安\",\"cityCode\":101270801},\n" +
            "{\"cityName\":\"巴中\",\"cityCode\":101270901},\n" +
            "{\"cityName\":\"泸州\",\"cityCode\":101271001},\n" +
            "{\"cityName\":\"宜宾\",\"cityCode\":101271101},\n" +
            "{\"cityName\":\"内江\",\"cityCode\":101271201},\n" +
            "{\"cityName\":\"资阳\",\"cityCode\":101271301},\n" +
            "{\"cityName\":\"乐山\",\"cityCode\":101271401},\n" +
            "{\"cityName\":\"眉山\",\"cityCode\":101271501},\n" +
            "{\"cityName\":\"凉山\",\"cityCode\":101271601},\n" +
            "{\"cityName\":\"雅安\",\"cityCode\":101271701},\n" +
            "{\"cityName\":\"甘孜\",\"cityCode\":101271801},\n" +
            "{\"cityName\":\"阿坝\",\"cityCode\":101271901},\n" +
            "{\"cityName\":\"德阳\",\"cityCode\":101272001},\n" +
            "{\"cityName\":\"广元\",\"cityCode\":101272101},\n" +
            "{\"cityName\":\"攀枝花\",\"cityCode\":101270201},\n" +
            "{\"cityName\":\"银川\",\"cityCode\":101170101},\n" +
            "{\"cityName\":\"中卫\",\"cityCode\":101170501},\n" +
            "{\"cityName\":\"固原\",\"cityCode\":101170401},\n" +
            "{\"cityName\":\"石嘴山\",\"cityCode\":101170201},\n" +
            "{\"cityName\":\"吴忠\",\"cityCode\":101170301},\n" +
            "{\"cityName\":\"西宁\",\"cityCode\":101150101},\n" +
            "{\"cityName\":\"黄南\",\"cityCode\":101150301},\n" +
            "{\"cityName\":\"海北\",\"cityCode\":101150801},\n" +
            "{\"cityName\":\"果洛\",\"cityCode\":101150501},\n" +
            "{\"cityName\":\"玉树\",\"cityCode\":101150601},\n" +
            "{\"cityName\":\"海西\",\"cityCode\":101150701},\n" +
            "{\"cityName\":\"海东\",\"cityCode\":101150201},\n" +
            "{\"cityName\":\"海南\",\"cityCode\":101150401},\n" +
            "{\"cityName\":\"济南\",\"cityCode\":101120101},\n" +
            "{\"cityName\":\"潍坊\",\"cityCode\":101120601},\n" +
            "{\"cityName\":\"临沂\",\"cityCode\":101120901},\n" +
            "{\"cityName\":\"菏泽\",\"cityCode\":101121001},\n" +
            "{\"cityName\":\"滨州\",\"cityCode\":101121101},\n" +
            "{\"cityName\":\"东营\",\"cityCode\":101121201},\n" +
            "{\"cityName\":\"威海\",\"cityCode\":101121301},\n" +
            "{\"cityName\":\"枣庄\",\"cityCode\":101121401},\n" +
            "{\"cityName\":\"日照\",\"cityCode\":101121501},\n" +
            "{\"cityName\":\"莱芜\",\"cityCode\":101121601},\n" +
            "{\"cityName\":\"聊城\",\"cityCode\":101121701},\n" +
            "{\"cityName\":\"青岛\",\"cityCode\":101120201},\n" +
            "{\"cityName\":\"淄博\",\"cityCode\":101120301},\n" +
            "{\"cityName\":\"德州\",\"cityCode\":101120401},\n" +
            "{\"cityName\":\"烟台\",\"cityCode\":101120501},\n" +
            "{\"cityName\":\"济宁\",\"cityCode\":101120701},\n" +
            "{\"cityName\":\"泰安\",\"cityCode\":101120801},\n" +
            "{\"cityName\":\"西安\",\"cityCode\":101110101},\n" +
            "{\"cityName\":\"延安\",\"cityCode\":101110300},\n" +
            "{\"cityName\":\"榆林\",\"cityCode\":101110401},\n" +
            "{\"cityName\":\"铜川\",\"cityCode\":101111001},\n" +
            "{\"cityName\":\"商洛\",\"cityCode\":101110601},\n" +
            "{\"cityName\":\"安康\",\"cityCode\":101110701},\n" +
            "{\"cityName\":\"汉中\",\"cityCode\":101110801},\n" +
            "{\"cityName\":\"宝鸡\",\"cityCode\":101110901},\n" +
            "{\"cityName\":\"咸阳\",\"cityCode\":101110200},\n" +
            "{\"cityName\":\"渭南\",\"cityCode\":101110501},\n" +
            "{\"cityName\":\"太原\",\"cityCode\":101100101},\n" +
            "{\"cityName\":\"临汾\",\"cityCode\":101100701},\n" +
            "{\"cityName\":\"运城\",\"cityCode\":101100801},\n" +
            "{\"cityName\":\"朔州\",\"cityCode\":101100901},\n" +
            "{\"cityName\":\"忻州\",\"cityCode\":101101001},\n" +
            "{\"cityName\":\"长治\",\"cityCode\":101100501},\n" +
            "{\"cityName\":\"大同\",\"cityCode\":101100201},\n" +
            "{\"cityName\":\"阳泉\",\"cityCode\":101100301},\n" +
            "{\"cityName\":\"晋中\",\"cityCode\":101100401},\n" +
            "{\"cityName\":\"晋城\",\"cityCode\":101100601},\n" +
            "{\"cityName\":\"吕梁\",\"cityCode\":101101100},\n" +
            "{\"cityName\":\"乌鲁木齐\",\"cityCode\":101130101},\n" +
            "{\"cityName\":\"石河子\",\"cityCode\":101130301},\n" +
            "{\"cityName\":\"昌吉\",\"cityCode\":101130401},\n" +
            "{\"cityName\":\"吐鲁番\",\"cityCode\":101130501},\n" +
            "{\"cityName\":\"库尔勒\",\"cityCode\":101130601},\n" +
            "{\"cityName\":\"阿拉尔\",\"cityCode\":101130701},\n" +
            "{\"cityName\":\"阿克苏\",\"cityCode\":101130801},\n" +
            "{\"cityName\":\"喀什\",\"cityCode\":101130901},\n" +
            "{\"cityName\":\"伊宁\",\"cityCode\":101131001},\n" +
            "{\"cityName\":\"塔城\",\"cityCode\":101131101},\n" +
            "{\"cityName\":\"哈密\",\"cityCode\":101131201},\n" +
            "{\"cityName\":\"和田\",\"cityCode\":101131301},\n" +
            "{\"cityName\":\"阿勒泰\",\"cityCode\":101131401},\n" +
            "{\"cityName\":\"阿图什\",\"cityCode\":101131501},\n" +
            "{\"cityName\":\"博乐\",\"cityCode\":101131601},\n" +
            "{\"cityName\":\"克拉玛依\",\"cityCode\":101130201},\n" +
            "{\"cityName\":\"拉萨\",\"cityCode\":101140101},\n" +
            "{\"cityName\":\"山南\",\"cityCode\":101140301},\n" +
            "{\"cityName\":\"阿里\",\"cityCode\":101140701},\n" +
            "{\"cityName\":\"昌都\",\"cityCode\":101140501},\n" +
            "{\"cityName\":\"那曲\",\"cityCode\":101140601},\n" +
            "{\"cityName\":\"日喀则\",\"cityCode\":101140201},\n" +
            "{\"cityName\":\"林芝\",\"cityCode\":101140401},\n" +
            "{\"cityName\":\"台北县\",\"cityCode\":101340101},\n" +
            "{\"cityName\":\"高雄\",\"cityCode\":101340201},\n" +
            "{\"cityName\":\"台中\",\"cityCode\":101340401},\n" +
            "{\"cityName\":\"海口\",\"cityCode\":101310101},\n" +
            "{\"cityName\":\"三亚\",\"cityCode\":101310201},\n" +
            "{\"cityName\":\"东方\",\"cityCode\":101310202},\n" +
            "{\"cityName\":\"临高\",\"cityCode\":101310203},\n" +
            "{\"cityName\":\"澄迈\",\"cityCode\":101310204},\n" +
            "{\"cityName\":\"儋州\",\"cityCode\":101310205},\n" +
            "{\"cityName\":\"昌江\",\"cityCode\":101310206},\n" +
            "{\"cityName\":\"白沙\",\"cityCode\":101310207},\n" +
            "{\"cityName\":\"琼中\",\"cityCode\":101310208},\n" +
            "{\"cityName\":\"定安\",\"cityCode\":101310209},\n" +
            "{\"cityName\":\"屯昌\",\"cityCode\":101310210},\n" +
            "{\"cityName\":\"琼海\",\"cityCode\":101310211},\n" +
            "{\"cityName\":\"文昌\",\"cityCode\":101310212},\n" +
            "{\"cityName\":\"保亭\",\"cityCode\":101310214},\n" +
            "{\"cityName\":\"万宁\",\"cityCode\":101310215},\n" +
            "{\"cityName\":\"陵水\",\"cityCode\":101310216},\n" +
            "{\"cityName\":\"西沙\",\"cityCode\":101310217},\n" +
            "{\"cityName\":\"南沙岛\",\"cityCode\":101310220},\n" +
            "{\"cityName\":\"乐东\",\"cityCode\":101310221},\n" +
            "{\"cityName\":\"五指山\",\"cityCode\":101310222},\n" +
            "{\"cityName\":\"琼山\",\"cityCode\":101310102},\n" +
            "{\"cityName\":\"长沙\",\"cityCode\":101250101},\n" +
            "{\"cityName\":\"株洲\",\"cityCode\":101250301},\n" +
            "{\"cityName\":\"衡阳\",\"cityCode\":101250401},\n" +
            "{\"cityName\":\"郴州\",\"cityCode\":101250501},\n" +
            "{\"cityName\":\"常德\",\"cityCode\":101250601},\n" +
            "{\"cityName\":\"益阳\",\"cityCode\":101250700},\n" +
            "{\"cityName\":\"娄底\",\"cityCode\":101250801},\n" +
            "{\"cityName\":\"邵阳\",\"cityCode\":101250901},\n" +
            "{\"cityName\":\"岳阳\",\"cityCode\":101251001},\n" +
            "{\"cityName\":\"张家界\",\"cityCode\":101251101},\n" +
            "{\"cityName\":\"怀化\",\"cityCode\":101251201},\n" +
            "{\"cityName\":\"黔阳\",\"cityCode\":101251301},\n" +
            "{\"cityName\":\"永州\",\"cityCode\":101251401},\n" +
            "{\"cityName\":\"吉首\",\"cityCode\":101251501},\n" +
            "{\"cityName\":\"湘潭\",\"cityCode\":101250201},\n" +
            "{\"cityName\":\"南京\",\"cityCode\":101190101},\n" +
            "{\"cityName\":\"镇江\",\"cityCode\":101190301},\n" +
            "{\"cityName\":\"苏州\",\"cityCode\":101190401},\n" +
            "{\"cityName\":\"南通\",\"cityCode\":101190501},\n" +
            "{\"cityName\":\"扬州\",\"cityCode\":101190601},\n" +
            "{\"cityName\":\"宿迁\",\"cityCode\":101191301},\n" +
            "{\"cityName\":\"徐州\",\"cityCode\":101190801},\n" +
            "{\"cityName\":\"哈尔滨\",\"cityCode\":101050101},\n" +
            "{\"cityName\":\"牡丹江\",\"cityCode\":101050301},\n" +
            "{\"cityName\":\"佳木斯\",\"cityCode\":101050401},\n" +
            "{\"cityName\":\"绥化\",\"cityCode\":101050501},\n" +
            "{\"cityName\":\"黑河\",\"cityCode\":101050601},\n" +
            "{\"cityName\":\"双鸭山\",\"cityCode\":101051301},\n" +
            "{\"cityName\":\"伊春\",\"cityCode\":101050801},\n" +
            "{\"cityName\":\"大庆\",\"cityCode\":101050901},\n" +
            "{\"cityName\":\"七台河\",\"cityCode\":101051002},\n" +
            "{\"cityName\":\"鸡西\",\"cityCode\":101051101},\n" +
            "{\"cityName\":\"鹤岗\",\"cityCode\":101051201},\n" +
            "{\"cityName\":\"齐齐哈尔\",\"cityCode\":101050201},\n" +
            "{\"cityName\":\"大兴安岭\",\"cityCode\":101050701},\n" +
            "{\"cityName\":\"长春\",\"cityCode\":101060101},\n" +
            "{\"cityName\":\"延吉\",\"cityCode\":101060301},\n" +
            "{\"cityName\":\"四平\",\"cityCode\":101060401},\n" +

            "{\"cityName\":\"沈阳\",\"cityCode\":101070101},\n" +
            "{\"cityName\":\"鞍山\",\"cityCode\":101070301},\n" +
            "{\"cityName\":\"抚顺\",\"cityCode\":101070401},\n" +
            "{\"cityName\":\"本溪\",\"cityCode\":101070501},\n" +
            "{\"cityName\":\"丹东\",\"cityCode\":101070601},\n" +
            "{\"cityName\":\"葫芦岛\",\"cityCode\":101071401},\n" +
            "{\"cityName\":\"营口\",\"cityCode\":101070801},\n" +
            "{\"cityName\":\"辽阳\",\"cityCode\":101071001},\n" +
            "{\"cityName\":\"盘锦\",\"cityCode\":101071301},\n" +
            "{\"cityName\":\"大连\",\"cityCode\":101070201},\n" +
            "{\"cityName\":\"锦州\",\"cityCode\":101070701}\n" +
            "]";

    public static int CityCodeManager(String cityname) {
int conde =0;
        cityCodeMap = new HashMap<>();
        tbCityCodeList = new ArrayList<>();
        List<Tb_CityCode> tbCityCodeList = JSON.parseArray(cityCode, Tb_CityCode.class);
        for (int i = 0; i < tbCityCodeList.size(); i ++) {
            tb_cityCode = tbCityCodeList.get(i);
            cityCodeMap.put(tb_cityCode.getCityName(), tb_cityCode.getCityCode());
            if (cityname.equals(tbCityCodeList.get(i).getCityName())) {
                conde = tbCityCodeList.get(i).getCityCode();
                return  conde;
            }
        }

        return  conde;
    }
    public static List<Tb_CityCode> CityCodeManager() {
        List<Tb_CityCode> tbCityCodeList = JSON.parseArray(cityCode, Tb_CityCode.class);

        return  tbCityCodeList;
    }


}
