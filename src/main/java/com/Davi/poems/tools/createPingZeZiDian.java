package com.Davi.poems.tools;


import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by David on 2017/3/20.
 */
public class createPingZeZiDian {
    DocumentBuilderFactory dbf;
    DocumentBuilder db;
    Document doc;
    TransformerFactory tff;
    Transformer tf;
    DOMSource domSource;
    ByteArrayOutputStream bos;
    File file;

    FileOutputStream out;

    StreamResult xmlResult;

    final ArrayList<Element> words = new ArrayList<Element>();
    public createPingZeZiDian(){
        try {
            dbf = DocumentBuilderFactory.newInstance();
            db = dbf.newDocumentBuilder();
            doc = db.newDocument();
            doc.setXmlVersion("1.0");
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }


    }

    public void buildXMLDoc (){
        String xmlStr;

        try {
            Element root = doc.createElement("root");
            doc.appendChild(root);

            for (int i = 0; i < words.size(); i++) {
                root.appendChild(words.get(i));
            }
            tff = TransformerFactory.newInstance();
            tf = tff.newTransformer();
            domSource = new DOMSource(doc);
            bos = new ByteArrayOutputStream();
            tf.transform(domSource, new StreamResult(bos));
            xmlStr = bos.toString();

            //save file
            this.saveFile();






        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    private void saveFile() throws IOException, TransformerException {
        file = new File("/pingze.xml");
        if (!file.exists()){
            file.createNewFile();
        }
        out = new FileOutputStream(file);
        xmlResult = new StreamResult(out);
        tf.transform(domSource,xmlResult);
    }

    /**
     *
     * @param str 每一韵的所有字符
     * @param pingZe 分为int 0-3 0：平 1：上 2：去 3：入
     * @param yunbu 平声共30韵，上声29韵，去声30韵，入声17韵
     *      上平声 0
     *      一东 二冬 三江 四支 五微 六鱼 七虞 八齐 九佳 十灰 十一真 十二文 十三元 十四寒 十五删
     *      下平声 0
     *      一先 二萧 三肴 四豪 五歌 六麻 七阳 八庚 九青 十蒸 十一尤 十二侵 十三覃 十四盐 十五咸
     *      上声 1
     *      一董  二肿  三讲  四纸  五尾  六语  七麌  八荠  九蟹  十贿  十一轸  十二吻  十三阮  十四旱  十五潸  十六铣  十七筱  十八巧  十九皓  二十哿  二十一马  二十二养  二十三梗  二十四迥  二十五有  二十六寝  二十七感  二十八琰  二十九豏
     *      去声 2
     *      一送 二宋 三绛 四寘 五未 六御 七遇 八霁 九泰 十卦 十一队 十二震 十三问 十四愿 十五翰 十六谏 十七霰 十八啸 十九效 二十号 二十一个 二十二祃 二十三漾 二十四敬 二十五径 二十六宥 二十七沁 二十八勘 二十九艳 三十陷
     *      入声 3
     *      一屋 二沃 三觉 四质 五物 六月 七曷 八黠 九屑 十药 十一陌 十二锡 十三职 十四缉 十五合 十六叶 十七洽
     */
    public void setWords (String str,int pingZe,int yunbu){
        for (String s: str.split("")) {
            words.add(getWord(s,pingZe,yunbu));
        }
    }

    public Element getWord(String word,int pingZe,int yunbu){
        Element w = doc.createElement("字符");
        w.setTextContent(word);
        return this.setWordPingZe(w,pingZe,yunbu);
    }

    public Element setWordPingZe(Element word,int pingZe,int yunbu){
        Element pz = doc.createElement("上平去入");
        pz.setTextContent(String.valueOf(pingZe));
        Element yun = doc.createElement("韵部");
        yun.setTextContent(String.valueOf(yunbu));
        word.appendChild(pz);
        word.appendChild(yun);
        return word;
    }

    public static void main(String[] args) {
        createPingZeZiDian cpzzd = new createPingZeZiDian();
        String str1 = "东同铜桐筒童僮瞳中衷忠虫终戎崇嵩弓躬宫融雄熊穹穷冯风枫丰充隆空公功工攻蒙笼聋珑洪红鸿虹丛翁聪通蓬烘潼胧砻峒螽梦讧冻忡酆恫总侗窿懵庞种盅芎倥艨绒葱匆骢";
        int pingZe1 = 0;
        int yun1 = 1;
        cpzzd.setWords(str1,pingZe1,yun1);
        String str2 = "冬农宗钟龙舂松冲容蓉庸封胸雍浓重从逢缝踪茸峰锋烽蛩慵恭供淙侬松凶墉镛佣溶邛共憧喁邕壅纵龚枞脓淞匈汹禺蚣榕彤";
        int pingZe2 = 0;
        int yun2 = 2;
        cpzzd.setWords(str2,pingZe2,yun2);
        String str3 = "江扛窗邦缸降双庞逄腔撞幢桩淙豇";
        int pingZe3 = 0;
        int yun3 = 3;
        cpzzd.setWords(str3,pingZe3,yun3);
        String str4 = "支枝移为垂吹陂碑奇宜仪皮儿离施知驰池规危夷师姿迟眉悲之芝时诗棋旗辞词期祠基疑姬丝司葵医帷思滋持随痴维卮麋螭麾墀弥慈遗肌脂雌披嬉尸狸炊篱兹差疲茨卑亏蕤陲骑曦歧岐谁斯私窥熙欺疵赀笞羁彝颐资糜饥衰锥姨楣夔涯伊蓍追缁箕椎罴篪萎匙脾坻嶷治骊尸綦怡尼漪累牺饴而鸱推縻璃祁绥逵羲羸肢骐訾狮奇嗤咨堕其睢漓蠡噫馗辎胝鳍蛇陴淇淄丽筛厮氏痍貔比僖贻祺嘻鹂瓷琦嵋怩熹孜台蚩罹魑丕琪耆衰惟剂提禧居栀戏畸椅磁痿离佳虽仔寅委崎隋逶倭黎犁郦";
        int pingZe4 = 0;
        int yun4 = 4;
        cpzzd.setWords(str4,pingZe4,yun4);
        String str5 = "微薇晖徽挥韦围帏违霏菲妃绯飞非扉肥腓威畿机几讥矶稀希?衣依沂巍归诽痱欷葳颀圻";
        int pingZe5 = 0;
        int yun5 = 5;
        cpzzd.setWords(str5,pingZe5,yun5);
        String str6 = "鱼渔初书舒居裾车渠余予誉舆胥狙锄疏蔬梳虚嘘徐猪闾庐驴诸除储如墟与畲疽苴于茹蛆且沮祛蜍榈淤好雎纾躇趄滁屠据匹咀衙涂虑";
        int pingZe6 = 0;
        int yun6 = 6;
        cpzzd.setWords(str6,pingZe6,yun6);
        String str7 = "虞愚娱隅刍无芜巫于盂衢儒濡襦须株诛蛛殊瑜榆谀愉腴区驱躯朱珠趋扶符凫雏敷夫肤纡输枢厨俱驹模谟蒲胡湖瑚乎壶狐弧孤辜姑觚菰徒途涂荼图屠奴呼吾梧吴租卢鲈苏酥乌枯都铺禺诬竽吁瞿劬需俞逾觎揄萸臾渝岖镂娄夫孚桴俘迂姝拘摹糊鸪沽呱蛄驽逋舻垆徂孥泸栌嚅蚨诹扶母毋芙喁颅轳句邾洙麸机膜瓠恶芋呕驺喻枸侏龉葫懦帑拊";
        int pingZe7 = 0;
        int yun7 = 7;
        cpzzd.setWords(str7,pingZe7,yun7);
        String str8 = "齐蛴脐黎犁梨黧妻萋凄堤低氐诋题提荑缔折篦鸡稽兮奚嵇蹊倪霓西栖犀嘶撕梯鼙批挤迷泥溪圭闺睽奎携畦骊鹂儿";
        int pingZe8 = 0;
        int yun8 = 8;
        cpzzd.setWords(str8,pingZe8,yun8);
        String str9 = "佳街鞋牌柴钗差涯阶偕谐骸排乖怀淮豺侪埋霾斋娲蜗娃哇皆喈揩蛙楷槐俳";
        int pingZe9 = 0;
        int yun9 = 9;
        cpzzd.setWords(str9,pingZe9,yun9);
        String str10 = "灰恢魁隈回徊枚梅媒煤瑰雷催摧堆陪杯醅嵬推开哀埃台苔该才材财裁来莱栽哉灾猜胎孩虺崔裴培坏垓陔徕皑傀崃诙煨桅唉颏能茴酶偎隗咳";
        int pingZe10 = 0;
        int yun10 = 10;
        cpzzd.setWords(str10,pingZe10,yun10);
        String str11 = "真因茵辛新薪晨辰臣人仁神亲申伸绅身宾滨邻鳞麟珍尘陈春津秦频苹颦银垠筠巾民珉缗贫淳醇纯唇伦纶轮沦匀旬巡驯钧均臻榛姻寅彬鹑皴遵循振甄岷谆椿询恂峋莘堙屯呻粼磷辚濒闽豳逡填狺泯洵溱夤荀竣娠纫鄞抡畛嶙斌氤";
        int pingZe11 = 0;
        int yun11 = 11;
        cpzzd.setWords(str11,pingZe11,yun11);
        String str12 = "文闻纹云氛分纷芬焚坟群裙君军勤斤筋勋薰曛熏荤耘芸汾氲员欣芹殷昕贲郧雯蕲";
        int pingZe12 = 0;
        int yun12 = 12;
        cpzzd.setWords(str12,pingZe12,yun12);
        String str13 = "元原源园猿辕坦烦繁蕃樊翻萱喧冤言轩藩魂浑温孙门尊存蹲敦墩暾屯豚村盆奔论坤昏婚阍痕根恩吞沅媛援爰幡番反埙鸳宛掀昆琨鲲扪荪髡跟垠抡蕴犍袁怨蜿溷昆炖饨臀喷纯";
        int pingZe13 = 0;
        int yun13 = 13;
        cpzzd.setWords(str13,pingZe13,yun13);
        String str14 = "寒韩翰丹殚单安难餐滩坛檀弹残干肝竿乾阑栏澜兰看刊丸桓纨端湍酸团抟攒官观冠鸾銮栾峦欢宽盘蟠漫汗郸叹摊奸剜棺钻瘢谩瞒潘胖弁拦完莞獾拌掸萑倌繁曼馒鳗谰洹滦";
        int pingZe14 = 0;
        int yun14 = 14;
        cpzzd.setWords(str14,pingZe14,yun14);
        String str15 = "删潸关弯湾还环鹌鬟寰班斑颁般蛮颜菅攀顽山鳏艰闲娴悭孱潺殷扳讪患";
        int pingZe15 = 0;
        int yun15 = 15;
        cpzzd.setWords(str15,pingZe15,yun15);
        String str16 = "先前千阡笺天坚肩贤弦烟燕莲怜田填钿年颠巅牵妍研眠渊涓蠲编玄县泉迁仙鲜钱煎然延筵禅蝉缠连联涟篇偏便全宣镌穿川缘鸢铅捐旋娟船涎鞭专圆员乾虔愆骞权拳椽传焉跹溅舷咽零骈阗鹃翩扁平沿诠痊悛荃遄卷挛戋佃滇婵颛犍搴嫣癣澶单竣鄢扇键蜷棉";
        int pingZe16 = 0;
        int yun16 = 16;
        cpzzd.setWords(str16,pingZe16,yun16);
        String str17 = "萧箫挑貂刁凋雕迢条跳苕调枭浇聊辽寥撩僚寮尧幺宵消霄绡销超朝潮嚣樵谯骄娇焦蕉椒饶烧遥姚摇谣瑶韶昭招飚标杓镳瓢苗描猫要腰邀乔桥侨妖夭漂飘翘祧佻徼侥哨娆陶橇劭潇骁獠料硝灶鹞钊蛲峤轿荞嘹逍燎憔剽";
        int pingZe17 = 0;
        int yun17 = 17;
        cpzzd.setWords(str17,pingZe17,yun17);
        String str18 = "肴巢交郊茅嘲钞包胶爻苞梢蛟庖匏坳敲胞抛鲛崤铙炮哮捎茭淆泡跑咬啁教咆鞘剿刨佼抓姣唠";
        int pingZe18 = 0;
        int yun18 = 18;
        cpzzd.setWords(str18,pingZe18,yun18);
        String str19 = "豪毫操髦刀萄猱桃糟漕旄袍挠蒿涛皋号陶翱敖遭篙羔高嘈搔毛艘滔骚韬缫膏牢醪逃槽劳洮叨绸饕骜熬臊涝淘尻挑嚣捞嗥薅咎谣";
        int pingZe19 = 0;
        int yun19 = 19;
        cpzzd.setWords(str19,pingZe19,yun19);
        String str20 = "歌多罗河戈阿和波科柯陀娥蛾鹅萝荷过磨螺禾哥娑驼佗沱峨那苛诃珂轲莎蓑梭婆摩魔讹坡颇俄哦呵皤么涡窝茄迦伽磋跎番蹉搓驮献蝌箩锅倭罗嵯锣";
        int pingZe20 = 0;
        int yun20 = 20;
        cpzzd.setWords(str20,pingZe20,yun20);
        String str21 = "麻花霞家茶华沙车牙蛇瓜斜邪芽嘉瑕纱鸦遮叉葩奢楂琶衙赊涯夸巴加耶嗟遐笳差蟆蛙虾拿葭茄挝呀枷哑娲爬杷蜗爷芭鲨珈骅娃哇洼畲丫夸裟瘕些桠杈痂哆爹椰咤笆桦划迦揶吾佘";
        int pingZe21 = 0;
        int yun21 = 21;
        cpzzd.setWords(str21,pingZe21,yun21);
        String str22 = "阳杨扬香乡光昌堂章张王房芳长塘妆常凉霜藏场央泱鸯秧嫱床方浆觞梁娘庄黄仓皇装殇襄骧相湘箱缃创忘芒望尝偿樯枪坊囊郎唐狂强肠康冈苍匡荒遑行妨棠翔良航倡伥羌庆姜僵缰疆粮穰将墙桑刚祥详洋徉佯粱量羊伤汤鲂樟彰漳璋猖商防筐煌隍凰蝗惶璜廊浪裆沧纲亢吭潢钢丧盲簧忙茫傍汪臧琅当庠裳昂障糖疡锵杭邙赃滂禳攘瓤抢螳踉眶炀阊彭蒋亡殃蔷镶孀搪彷胱磅膀螃";
        int pingZe22 = 0;
        int yun22 = 22;
        cpzzd.setWords(str22,pingZe22,yun22);
        String str23 = "庚更羹盲横觥彭棚亨英瑛烹平评京惊荆明盟鸣荣莹兵卿生甥笙牲檠擎鲸迎行衡耕萌氓宏闳茎莺樱泓橙筝争清情晴精睛菁旌晶盈瀛嬴营婴缨贞成盛城诚呈程声征正轻名令并倾萦琼赓撑瞠枪伧峥猩珩蘅铿嵘丁嘤鹦铮砰绷轰訇瞪侦顷榜抨趟坪请";
        int pingZe23 = 0;
        int yun23 = 23;
        cpzzd.setWords(str23,pingZe23,yun23);
        String str24 = "青经泾形刑邢型陉亭庭廷霆蜓停丁宁钉仃馨星腥醒惺娉灵棂龄铃苓伶零玲翎瓴囹聆听厅汀冥溟螟铭瓶屏萍荧萤荥扃町瞑暝";
        int pingZe24 = 0;
        int yun24 = 24;
        cpzzd.setWords(str24,pingZe24,yun24);
        String str25 = "蒸承丞惩陵凌绫冰膺鹰应蝇绳渑乘升胜兴缯凭仍兢矜征凝称登灯僧增曾憎层能棱朋鹏弘肱腾滕藤恒冯瞢扔誊";
        int pingZe25 = 0;
        int yun25 = 25;
        cpzzd.setWords(str25,pingZe25,yun25);
        String str26 = "尤邮优忧流留榴骝刘由油游猷悠攸牛修羞秋周州洲舟酬仇柔俦畴筹稠邱抽湫遒收鸠不愁休囚求裘球浮谋牟眸矛侯猴喉讴沤鸥瓯楼娄陬偷头投钩沟幽彪疣绸浏瘤犹啾酋售蹂揉搜叟邹貅泅球逑俅蜉桴罘欧搂抠髅蝼兜句妯惆呕缪繇偻篓馗区";
        int pingZe26 = 0;
        int yun26 = 26;
        cpzzd.setWords(str26,pingZe26,yun26);
        String str27 = "侵寻浔林霖临针箴斟沈深淫心琴禽擒钦衾吟今襟金音阴岑簪琳琛椹谌忱壬任黔歆禁喑森参淋郴妊湛";
        int pingZe27 = 0;
        int yun27 = 27;
        cpzzd.setWords(str27,pingZe27,yun27);
        String str28 = "覃潭谭参骖南男谙庵含涵函岚蚕探贪耽龛堪戡谈甘三酣篮柑惭蓝郯婪庵颔褴澹";
        int pingZe28 = 0;
        int yun28 = 28;
        cpzzd.setWords(str28,pingZe28,yun28);
        String str29 = "盐檐廉帘嫌严占髯谦奁纤签瞻蟾炎添兼缣尖潜阎镰粘淹箝甜恬拈暹詹渐歼黔沾苫占崦阉砭";
        int pingZe29 = 0;
        int yun29 = 29;
        cpzzd.setWords(str29,pingZe29,yun29);
        String str30 = "咸缄谗衔岩帆衫杉监凡馋芟喃嵌掺搀严";
        int pingZe30 = 0;
        int yun30 = 30;
        cpzzd.setWords(str30,pingZe30,yun30);

        //去声
        String strs1 = "董动孔总笼汞桶空拢洞懂侗";
        int pingZes1 = 1;
        int yuns1 = 1;
        cpzzd.setWords(strs1,pingZes1,yuns1);
        String strs2 = "肿种踵宠陇垄拥壅冗茸重冢奉捧勇涌踊俑蛹恐拱巩竦悚耸溶";
        int pingZes2 = 1;
        int yuns2 = 2;
        cpzzd.setWords(strs2,pingZes2,yuns2);
        String strs3 = "讲港棒蚌项耩";
        int pingZes3 = 1;
        int yuns3 = 3;
        cpzzd.setWords(strs3,pingZes3,yuns3);
        String strs4 = "纸只咫是枳砥抵氏靡彼毁委诡傀髓妓绮此褫徙髀尔迩弭弥婢侈弛豕紫捶揣企旨指视美訾否兕几姊匕比妣轨水唯止市徵喜已纪跪技迤鄙晷宄子梓矢雉死履垒诔揆癸趾芷以已似姒巳祀史使驶耳里理李俚鲤起杞士仕俟始峙痔齿矣拟耻滓玺跬圮痞址悝娌秭倚被你仔";
        int pingZes4 = 1;
        int yuns4 = 4;
        cpzzd.setWords(strs4,pingZes4,yuns4);
        String strs5 = "尾鬼苇卉虺几伟韪炜斐诽菲岂匪蜚";
        int pingZes5 = 1;
        int yuns5 = 5;
        cpzzd.setWords(strs5,pingZes5,yuns5);
        String strs6 = "语圉圄御吕侣旅膂抒宁杼与予渚煮汝茹暑鼠黍杵处贮褚女许拒距炬所楚础阻俎沮举莒序绪屿墅著巨讵咀纾去";
        int pingZes6 = 1;
        int yuns6 = 6;
        cpzzd.setWords(strs6,pingZes6,yuns6);
        String strs7 = "麌雨羽禹宇舞父府鼓虎古股贾土吐圃谱庾户树煦琥怙嵝篓卤努肚沪枸辅组乳弩补鲁橹睹竖腐数簿姥普拊侮五斧聚午伍缕部柱矩武脯苦取抚浦主杜祖堵愈祜扈雇虏甫腑俯估诂牯瞽酤怒浒诩栩拄剖鹉溥赌伛偻莽滏";
        int pingZes7 = 1;
        int yuns7 = 7;
        cpzzd.setWords(strs7,pingZes7,yuns7);
        String strs8 = "荠礼体米启醴陛洗邸底诋抵坻弟悌递涕济澧祢";
        int pingZes8 = 1;
        int yuns8 = 8;
        cpzzd.setWords(strs8,pingZes8,yuns8);
        String strs9 = "蟹解骇买洒楷锴摆拐矮伙";
        int pingZes9 = 1;
        int yuns9 = 9;
        cpzzd.setWords(strs9,pingZes9,yuns9);
        String strs10 = "贿悔改采彩海在宰醢载铠恺待怠殆倍猥蕾诒蓓鼐颏浼汇璀每亥乃";
        int pingZes10 = 1;
        int yuns10 = 10;
        cpzzd.setWords(strs10,pingZes10,yuns10);
        String strs11 = "轸敏允引尹尽忍准隼笋盾闵悯泯菌蚓诊畛肾牝赈窘蜃陨殒蠢紧缜纯吮朕稹嶙";
        int pingZes11 = 1;
        int yuns11 = 11;
        cpzzd.setWords(strs11,pingZes11,yuns11);
        String strs12 = "吻粉蕴愤隐谨近恽忿坟刎殷";
        int pingZes12 = 1;
        int yuns12 = 12;
        cpzzd.setWords(strs12,pingZes12,yuns12);
        String strs13 = "阮远本晚苑返反阪损饭偃堰稳蹇犍婉蜿宛阃鲧捆很恳垦圈盾绻混沌娩棍";
        int pingZes13 = 1;
        int yuns13 = 13;
        cpzzd.setWords(strs13,pingZes13,yuns13);
        String strs14 = "旱暖管满短馆盥缓碗款懒卵散伴诞浣瓒断侃算疃但坦袒悍懑纂趱";
        int pingZes14 = 1;
        int yuns14 = 14;
        cpzzd.setWords(strs14,pingZes14,yuns14);
        String strs15 = "潸眼版产限撰栈绾赧羼柬拣莞板";
        int pingZes15 = 1;
        int yuns15 = 15;
        cpzzd.setWords(strs15,pingZes15,yuns15);
        String strs16 = "铣善遣浅典转衍犬选冕辇免展茧辩篆勉翦卷显践饯眄喘软蹇演岘栈扁阐娈跣腆鲜戬吮辫件琏蠕单殄腼蚬缅沔键搴冼燹癣狷钱趁匾宴";
        int pingZes16 = 1;
        int yuns16 = 16;
        cpzzd.setWords(strs16,pingZes16,yuns16);
        String strs17 = "筱小表鸟了晓少扰绕娆绍秒沼眇矫蓼皎杳窈袅窕挑掉渺缈藐淼娇标悄缭僚昭夭燎赵兆";
        int pingZes17 = 1;
        int yuns17 = 17;
        cpzzd.setWords(strs17,pingZes17,yuns17);
        String strs18 = "巧饱卯狡爪鲍挠搅绞拗姣炒";
        int pingZes18 = 1;
        int yuns18 = 18;
        cpzzd.setWords(strs18,pingZes18,yuns18);
        String strs19 = "皓宝藻早枣老好道稻造脑恼岛倒祷抱讨考燥嫂槁潦保葆堡褓草昊浩颢镐皂袄缫蚤澡灏媪杲缟涝";
        int pingZes19 = 1;
        int yuns19 = 19;
        cpzzd.setWords(strs19,pingZes19,yuns19);
        String strs20 = "哿火舸柁沱我娜荷可坷轲左果裹朵锁琐堕垛惰妥坐裸跛簸颇叵祸卵娑爹揣隋";
        int pingZes20 = 1;
        int yuns20 = 20;
        cpzzd.setWords(strs20,pingZes20,yuns20);
        String strs21 = "马下者野雅瓦寡社写泻夏冶也把贾假舍赭厦惹若踝姐哆哑且瘕洒";
        int pingZes21 = 1;
        int yuns21 = 21;
        cpzzd.setWords(strs21,pingZes21,yuns21);
        String strs22 = "养痒鞅怏泱像象橡仰朗奖浆敞氅枉沆荡惘放仿两傥杖响掌党想爽广享丈仗幌晃莽襁纺蒋攘盎脏苍长上网荡壤赏往罔蟒魍抢慌厂慷向";
        int pingZes22 = 1;
        int yuns22 = 22;
        cpzzd.setWords(strs22,pingZes22,yuns22);
        String strs23 = "梗影景井岭领境警请屏饼永骋逞颍颖顷整静省幸颈郢猛炳杏丙打哽秉耿憬冷靖睛";
        int pingZes23 = 1;
        int yuns23 = 23;
        cpzzd.setWords(strs23,pingZes23,yuns23);
        String strs24 = "迥炯茗挺艇町醒溟酊刭等鼎顶胫肯拯酩";
        int pingZes24 = 1;
        int yuns24 = 24;
        cpzzd.setWords(strs24,pingZes24,yuns24);
        String strs25 = "有酒首手口母后柳友妇斗狗久负厚走守绶右否受牖偶耦阜九后咎吼帚垢亩舅藕朽臼肘韭剖诱牡缶酉扣欧黝蹂取钮莠丑苟糗某玖拇纣纠枸忸浏赳蚪培擞趣陡寿殴";
        int pingZes25 = 1;
        int yuns25 = 25;
        cpzzd.setWords(strs25,pingZes25,yuns25);
        String strs26 = "寝饮锦品枕审甚衽饪稔禀沈凛荏恁婶";
        int pingZes26 = 1;
        int yuns26 = 26;
        cpzzd.setWords(strs26,pingZes26,yuns26);
        String strs27 = "感览榄胆澹啖坎惨敢颔撼毯喊橄嵌";
        int pingZes27 = 1;
        int yuns27 = 27;
        cpzzd.setWords(strs27,pingZes27,yuns27);
        String strs28 = "琰焰敛俭险检脸染掩点贬冉陕谄奄渐玷忝闪歉广俨";
        int pingZes28 = 1;
        int yuns28 = 28;
        cpzzd.setWords(strs28,pingZes28,yuns28);
        String strs29 = "豏槛范减舰犯湛斩黯掺阚喊滥歉";
        int pingZes29 = 1;
        int yuns29 = 29;
        cpzzd.setWords(strs29,pingZes29,yuns29);


        //去声
        int yunq = 2;
        String[] pingZeqs = new String[30];
        pingZeqs[0] = "送梦凤洞众弄贡冻痛栋仲中讽恸空控赣砻哄衷";
        pingZeqs[1] = "宋重用颂诵统纵讼种综俸共供从缝雍封恐";
        pingZeqs[2] = "绛降巷撞虹洚淙";
        pingZeqs[3] = "寘置事地意志治思泪吏赐字义利器位戏至次累伪寺瑞智记异致肆翠骑使试类弃饵媚鼻易辔坠醉议翅避粹侍谊帅厕寄睡忌萃穗臂嗣吹遂恣四骥季刺驷识痣志寐魅邃燧隧谥植织饲食积被芰懿悸觊冀暨匮馈篑比庇畀痹毖泌鸷贽挚渍迟祟豉珥示伺嗜自詈痢莉譬肄惴劓啻企腻施遗值柴出萎司诿陂二近始术瑟德";
        pingZeqs[4] = "未味气贵费沸尉畏慰蔚魏胃渭谓讳卉毅溉既衣忾诽痱蜚翡";
        pingZeqs[5] = "御处去虑誉署据驭曙助絮著豫翥恕与遽疏庶诅预茹语踞狙沮除如女讵欤楚嘘";
        pingZeqs[6] = "遇路赂露鹭树度渡赋布步固素具数怒务雾鹜骛附兔故顾雇句墓暮慕募注驻祚裕误悟寤住戍库护诉蠹妒惧趣娶铸傅付谕妪捕哺忤措错醋赴恶互孺怖煦寓酤瓠输吐屡塑捂瞿驱讣属作酗雨获镀圃驸足播苦铺姹";
        pingZeqs[7] = "霁制计势世丽岁卫济第艺惠慧币桂滞际厉涕契毙帝蔽敝锐戾裔袂系祭隶闭逝缀替细税例誓蕙偈诣砺励噬继谛系剂曳睇憩彗逮芮掣蓟妻挤弟题鳜蹶齐棣说彘离荔泥蜕赘揭唳泄娣薜呓濞捩羿谜缔切医";
        pingZeqs[8] = "泰会带外盖大濑赖蔡害最贝霭沛艾兑奈绘桧脍会太汰癞粝蜕酹狈";
        pingZeqs[9] = "卦挂懈隘卖画派债怪坏诫戒界介芥械拜快迈话败稗噫疥瀣湃聩惫杀喝解祭蒯喟呗寨";
        pingZeqs[10] = "队内塞爱辈佩代退载碎背秽菜对废诲晦昧戴贷配妹溃黛赉吠逮岱肺溉耒慨块赛刈耐悖淬敦铠焙在再孛柿睐裁采回粹栽北劾悔";
        pingZeqs[11] = "震信印进润阵镇填刃顺慎鬓晋骏闰峻衅振舜吝烬讯胤殡迅瞬谆馑蔺徇赈觐摈仅认衬瑾趁韧汛磷躏浚缙娠引诊蜃亲";
        pingZeqs[12] = "问闻运晕韵训粪奋忿郡分紊汶愠靳近斤郓员拚隐";
        pingZeqs[13] = "愿论怨恨万饭献健寸困顿建宪劝蔓券钝闷逊嫩贩溷远曼喷艮敦郾褪堰圈";
        pingZeqs[14] = "翰岸汉难断乱叹干观散奈旦算玩烂贯半案按炭汗赞漫冠灌窜幔灿璨换焕唤悍弹惮段看判叛腕涣绊惋钻缦锻瀚胖谰蒜泮谩摊侃馆滩盥";
        pingZeqs[15] = "谏雁患涧闲宦晏慢盼豢栈惯串苋绽幻讪绾谩汕疝瓣篡铲栅扮";
        pingZeqs[16] = "霰殿面县变箭战扇煽膳传见砚选院练燕宴贱电荐绢彦甸便眷线倦羡堰奠遍恋眩钏倩卞汴弁拚咽片禅谴谚缘颤擅援媛瑗佃钿淀狷煎悬袖穿茜溅拣缠牵先炫善缱遣研衍辗转饯";
        pingZeqs[17] = "啸笑照庙窍妙诏召邵要曜耀调钓吊叫燎峤少眺诮料肖尿剽掉鹞粜轿烧疗漂醮骠绕娆摇哨约嘹裱";
        pingZeqs[18] = "效教貌校孝闹淖豹爆罩拗窖酵稍乐较钞敲觉";
        pingZeqs[19] = "号帽报导盗操噪灶奥告诰暴好到蹈劳傲躁涝漕造冒悼倒骜缟懊澳膏犒郜瀑旄靠糙";
        pingZeqs[20] = "箇个贺佐作逻坷轲大饿奈那些过和挫课唾簸磨座坐破卧货左惰";
        pingZeqs[21] = "禡驾夜下谢榭罢夏暇霸灞嫁赦借藉炙蔗假化舍价射骂稼架诈亚罅跨麝咤怕讶诧迓胯柘卸泻靶乍桦杷";
        pingZeqs[22] = "漾上望相将状帐浪唱让旷壮放向仗畅量葬匠障谤尚涨饷样藏舫访养酱嶂抗当酿亢况脏瘴王谅亮妄丧怅两圹宕忘傍砀恙吭炀张行广汤炕长创诳掠妨旺荡防怏偿荡盎仰挡傥";
        pingZeqs[23] = "敬命正令政性镜盛行圣咏姓庆映病柄郑劲竞净竟孟聘诤泳请倩硬檠晟更横榜迎娉轻评证侦并盟";
        pingZeqs[24] = "径定听胜磬应乘媵赠佞称罄邓胫莹证孕兴经醒廷锭庭钉暝剩凭凝橙凳蹬";
        pingZeqs[25] = "宥候就授售寿秀绣宿奏富兽斗漏陋守狩昼寇茂懋旧胃宙袖岫柚覆复救臭幼佑右侑囿豆窦逗溜瘤留构遘媾购透瘦漱镂鹫走副诟究凑谬缪疚灸畜柩骤首皱绉戊句鼬蹂沤又逅蔻伏收犹油后厚扣吼读";
        pingZeqs[26] = "沁饮禁任荫谶浸鸩枕衽赁临渗妊吟深甚沈";
        pingZeqs[27] = "勘暗滥担憾缆瞰三暂参澹淡憨淦";
        pingZeqs[28] = "艳剑念验赡店占敛厌滟垫欠僭砭餍殓苫盐沾兼念埝俺潜忝";
        pingZeqs[29] = "陷鉴监汛梵帆忏赚蘸谗剑欠淹站";

        for (int i = 1; i < 31; i++) {
            cpzzd.setWords(pingZeqs[i-1],yunq,i);
        }

        //入声
        int yunr = 3;
        String[] pingZers = new String[17];
        pingZers[0] = "屋木竹目服福禄熟谷肉咒鹿腹菊陆轴逐牧伏宿读犊渎牍椟黩毂复粥肃育六缩哭幅斛戮仆畜蓄叔淑菽独卡馥沐速祝麓镞蹙筑穆睦啄覆鹜秃扑鬻辐瀑竺簇暴掬濮郁矗复塾朴蹴煜谡碌毓舳柚蝠辘夙蝮匐觫囿苜茯髑副孰谷";
        pingZers[1] = "沃俗玉足曲粟烛属录辱狱绿毒局欲束鹄蜀促触续督赎浴酷瞩躅褥旭欲渌逯告仆";
        pingZers[2] = "觉角桷较岳乐捉朔数卓汲琢剥趵爆驳邈雹璞朴确浊擢镯濯幄喔药握搦学";
        pingZers[3] = "质日笔出室实疾术一乙壹吉秩密率律逸佚失漆栗毕恤蜜橘溢瑟膝匹黜弼七叱卒虱悉谧轶诘戌佶栉昵窒必侄蛭泌秫蟀嫉唧怵帅聿郅桎茁汨尼蒺";
        pingZers[4] = "物佛拂屈郁乞掘讫吃绂弗诎崛勿熨厥迄不屹芴倔尉蔚";
        pingZers[5] = "月骨发阙越谒没伐罚卒竭窟笏钺歇突忽勃蹶筏厥蕨掘阀讷殁粤悖兀碣猝樾羯汨咄渤凸滑孛纥核饽垡阏堀曰讦";
        pingZers[6] = "曷达末阔活钵脱夺褐割沫拔葛渴拨豁括聒抹秣遏挞萨掇喝跋獭撮剌泼斡捋袜适咄妲";
        pingZers[7] = "黠札拔猾八察杀刹轧刖戛秸嘎瞎刮刷滑";
        pingZers[8] = "屑节雪绝列烈结穴说血舌洁别裂热决铁灭折拙切悦辙诀泄咽噎杰彻别哲设劣碣掣谲窃缀阅抉挈捩楔蹩亵蔑捏竭契疖涅颉撷撤跌蔑浙澈蛭揭啜辍迭呐侄冽掇批橇";
        pingZers[9] = "药薄恶略作乐落阁鹤爵若约脚雀幕洛壑索郭博错跃若缚酌托削铎灼凿却络鹊度诺橐漠钥著虐掠获泊搏勺酪谑廓绰霍烁莫铄缴谔鄂亳恪箔攫涸疟郝骆膜粕礴拓蠖鳄格昨柝摸貉愕柞寞膊魄烙焯厝噩泽矍各猎昔芍踱迮";
        pingZers[10] = "陌石客白泽伯迹宅席策碧籍格役帛戟璧驿麦额柏魄积脉夕液册尺隙逆画百辟赤易革脊获翮屐适剧碛隔益栅窄核掷责惜僻癖辟掖腋释舶拍择摘射斥弈奕迫疫译昔瘠赫炙谪虢腊硕螫藉翟亦鬲骼鲫借啧蜴帼席貊汐摭咋吓剌百莫蝈绎霸霹";
        pingZers[11] = "锡壁历枥击绩笛敌滴镝檄激寂翟逖籴析晰溺觅摘狄荻戚涤的吃霹沥惕踢剔砾栎适嫡阋觋淅晰吊霓倜";
        pingZers[12] = "职国德食蚀色力翼墨极息直得北黑侧饰贼刻则塞式轼域殖植敕饬棘惑默织匿亿忆特勒劾仄稷识逼克蜮唧即拭弋陟测冒抑恻肋亟殛忒嶷熄穑啬匐鲫或愎翌";
        pingZers[13] = "缉辑立集邑急入泣溼习给十拾什袭及级涩粒揖汁蛰笠执隰汲吸熠岌歙熠挹";
        pingZers[14] = "合塔答纳榻杂腊蜡匝阖蛤衲沓鸽踏飒拉盍搭溘嗑";
        pingZers[15] = "叶帖贴牒接猎妾蝶箧涉捷颊楫摄蹑谍协侠荚睫慑蹀挟喋燮褶靥烨摺辄捻婕聂霎";
        pingZers[16] = "洽狭峡法甲业邺匣压鸭乏怯劫胁插押狎掐夹恰眨呷喋札钾";




        for (int i = 1; i < 18; i++) {
            cpzzd.setWords(pingZers[i-1],yunr,i);
        }
        cpzzd.buildXMLDoc();




    }
}
