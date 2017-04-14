package com.Davi.poems.tools;



import com.Davi.poems.basic.author;
import com.Davi.poems.basic.tangClass;
import com.Davi.poems.basic.wordClass;
import org.apache.log4j.Logger;
import sun.rmi.runtime.Log;

import javax.swing.text.html.HTMLDocument;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by wei on 2017/2/27.
 */
public class analyze {

    Logger logger = Logger.getLogger(analyze.class);

    private static wordSource wordSource = new wordSource();
    private static pzSource pzSource = new pzSource();
    private static kindSource ks = new kindSource();
    private static authorSource as = new authorSource();
    private static tangSource ts = new tangSource();
    private static createOrderWordsList cowl = new createOrderWordsList();

    private char[] SET = {'？', '?', '|', '&', '[', ']', '{', '}', '*', '(', ')', '（', '）', '+'};

    private char[] spliter = {'？', '?', '。', '，', '\n'};

    private static ArrayList<wordClass> pingLists;
    private static ArrayList<wordClass> zeLists;

    public analyze() {
    }

    /**
     * 获取精确查找时的字符串长度
     * @param s 查询语句不包括诗人
     * @return
     * @throws myException
     */
    public int getLength(String s) throws myException {
        if (s.contains("?") | s.contains("？")) {
            throw new myException("uncertain length");
        }
        if (s.indexOf('&') >= 0) {
            String subString = s.substring(s.indexOf('&') + 1);
            return getLength(subString);
        } else {
            int length = 0;
            /*
            for (int i = 0 ; i < s.length() ; i++) {
                if(s.charAt(i) == '('){
                    i = s.indexOf(')',i);
                }else if(s.charAt(i) == '['){
                    i = s.indexOf(']',i);
                    length++;
                }else if(s.charAt(i) == '{'){
                    i = s.indexOf('}',i);
                }else if(s.charAt(i) == '*'){
                    length++;
                }else if(s.charAt(i) == 'P'){
                    length++;
                }else if(s.charAt(i) == 'Z'){
                    length++;
                }else
                    length++;
            }*/
            for (int i = 0; i < s.length(); i++) {
                switch (s.charAt(i)) {
                    case '(':
                        //修饰韵母
                        i = s.indexOf(')', i);
                        break;
                    case '（':
                        i = s.indexOf('）', i);
                        break;
                    case '[':
                        i = s.indexOf(']', i);
                        length++;
                        break;
                    case '{':
                        //修饰词性
                        i = s.indexOf('}', i);
                        break;
                    case '*':
                        length++;
                        break;
                    case 'P':
                        length++;
                        break;
                    case 'Z':
                        length++;
                        break;
                    default:
                        length++;

                }

            }
            return length;
        }

    }

    /**
     * 获取诗句的长度
     * @param s
     * @return
     * @throws myException
     */

    public int getLengthFromRubbish(String s) throws myException {
        int result = 0;
        for (int i = 0; i < s.length(); i++) {
            if (isChinese(s.charAt(i))) {
                result++;
            }
        }
        return result;
    }

    /**
     * 获得每一个目的匹配的元素，方便逐字判断是否符合
     * 目前仅适用于匹配模式
     *
     * @param s
     * @return
     * @throws myException
     */
    public HashMap<Integer, String> getTagetString(String s) throws myException {
        if (s.contains("?") | s.contains("？")) {
            throw new myException("uncertain length");
        }
        if (s.indexOf('&') >= 0) {
            String subString = s.substring(s.indexOf('&') + 1);
            return getTagetString(subString);
        } else {
            int length = 0;
            HashMap<Integer, String> result = new HashMap<>();
            int j = 0, k = 0;
            for (int i = 0; i < s.length(); i++) {
                int oldLength = length;
                switch (s.charAt(i)) {
                    case '(':
                        k = i;
                        i = s.indexOf(')', i);
                        break;
                    case '（':
                        i = s.indexOf('）',i);
                        break;
                    case '[':
                        j = i;
                        i = s.indexOf(']', i);
                        length++;
                        break;
                    case '{':
                        k = i;
                        i = s.indexOf('}', i);
                        break;
                    case '*':
                        j = i;
                        length++;
                        break;
                    case 'P':
                        j = i;
                        length++;
                        break;
                    case 'Z':
                        j = i;
                        length++;
                        break;
                    default:
                        j = i;
                        length++;

                }
                //将每一个字符分开
                if (oldLength != length)
                    result.put(length, s.substring(j, i + 1));
                else {
                    result.put(length, s.substring(j, i + 1));
                }

            }
            return result;
        }
    }

    /**
     * 将诗句中的文字进行hashMap的映射没便于逐字匹配
     * @param s
     * @return
     * @throws myException
     */

    public HashMap<Integer, String> getTagetFromRubbish(String s) throws myException {
        HashMap<Integer, String> result = new HashMap<>();
        int length = 0;
        for (int i = 0; i < s.length(); i++) {
            if (isChinese(s.charAt(i))) {
                result.put(++length, s.substring(i, i + 1));
            }
        }
        return result;
    }

    public static boolean isChinese(char c) {
        return c >= 0x4E00 && c <= 0x9FA5;// 根据字节码判断
    }


    /**
     * 获取作者的数组，如果语法错误或者没有要求作者做处理
     *
     * @return
     * @throws myException
     */
    public String[] getAuthor(String s) throws myException {
        //不含诗人条件
        if (s == "" || s == null){
            throw new myException("无诗人输入");
        }
        if (!s.contains("&")) {
            return null;
        }
        //找到诗人的分隔符
        String[] subString = s.split("&");
        if (subString.length == 0)
            throw new myException("无诗人输入,或者错误使用字符 &");
        if (subString.length > 2)
            throw new myException("多&分隔符");
        //获得诗人姓名数组
        if (subString[0].contains("|")) {
            String[] subAuthor = subString[0].split("\\|");
            return subAuthor;
        } else if ( s.charAt(0) == '&'){
            throw new myException("空作者输入");
        }else{
            String[] result = {subString[0]};
            return result;
        }

    }

    /**
     * 获取单字的部分[]
     *
     * @return
     * @throws myException
     */
    public String[] getOrOther(String s) throws myException {
        if (s.contains("[") && s.contains("]")) {
            //获得[]的部分
            int first = -1, end = -1;
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == '[') {
                    first = i;
                } else if (s.charAt(i) == ']') {
                    end = i;
                }
            }
            if (first > -1 && end > -1) {
                s = s.substring(first + 1, end);
            }
            return s.split("");

        } else {
            throw new myException("非法输入,不含[]");
        }


    }

    /**
     * 判断句子是不是变长匹配
     *
     * @param input
     * @return
     */
    public boolean isMutiLength(String input) {
        if (input.contains("?") || input.contains("？"))
            return true;
        else
            return false;
    }


    /**
     * 这个方法需要配合getTagetString 解析器中的每一个韵母约束
     *
     * @param
     * @return
     * @throws myException
     */
    public String getPinYin(String tmp) throws myException {
        String result = "";
        if ((tmp.contains("(") && tmp.contains(")")) ||(tmp.contains("（") && tmp.contains("）"))) {
            int first = -1, end = -1;
            //获得唯一的（）子句index
            for (int i = 0; i < tmp.length(); i++) {
                if (first > -1 && end > -1) {
                    break;
                } else {
                    if (tmp.charAt(i) == '(' || tmp.charAt(i) == '（') {
                        first = i;
                    } else if (tmp.charAt(i) == ')' || tmp.charAt(i) == '）') {
                        end = i;
                    }
                }
            }
            if (first > -1 && end > -1)
                result = tmp.substring(first + 1, end);
        }


        return result;
    }


    /**
     * 判断1个字符与一个关键字约束是否匹配
     * @param a 搜索规则，已经逐字解析
     * @param b 数据语句单字
     * @return
     * @throws myException
     */
    public boolean judge(String a, String b) throws myException {
        if (a == null) return false;
        if (b == null) return false;
        if (a == "") return false;
        if (a.equals("*")) {
            return true;
        } else if (a.equals(b.trim())) {
            return true;
        } else if (a.equals("P") || a.equals("p")) { //平仄约束
            if (getPZ(b).equals("P")) {
                return true;
            }
            return false;
        } else if (a.equals("Z") || a.equals("z")) {
            if (getPZ(b).equals("Z")) {
                return true;
            }
            return false;
        }

        //b是复杂约束
        //1 []约束，任选约束
        boolean word = false;
        if (a.contains("[")) {
            String[] stringArray = getOrOther(a);
            for (String tmp : stringArray) {
                if (tmp.equals(b)) {
                    word = true;
                }
            }
        }
        //如果是 *() *{}这类的词，直接通过
        if (a.contains("*")){
            word = true;
        }
        //2 ()约束,拼音约束
        boolean isPinYin = false;
        if (a.contains("(") || a.contains("（")) {
            String PinYin = getPinYin(a);
            int length = PinYin.length();
            for (String s : pinYinIs(b)) {
                //拼音有后缀声调

                if (s.length() >= length ) {

                    //System.out.print(b);
                    if (s.subSequence(0, length).equals(PinYin)) {
                        isPinYin = true;
                        break;
                    }
                }
            }
        } else {
            //无拼音约束
            isPinYin = true;
        }
        //3 判断词性
        //todo: 完成词性
        boolean isCiXing = true;


        //拼音约束与字约束同时满足
        if (word && isPinYin && isCiXing) {
            return true;
        } else {
            return false;
        }


    }

    /**
     *
     *  获得一个字符的全部读音
     * @param b
     * @return
     */
    public String[] pinYinIs(String b) {
        Iterator<wordClass> iterator = wordSource.getWordClassArrayList().iterator();
        while (iterator.hasNext()){
            wordClass word = iterator.next();
            if (word.getWord().equals(b)){
                return word.getPinYin();
            }
        }
        return new String[]{""};
    }
    /**
     *
     *
     *      平仄 分为int 0-3 0：平 1：上 2：去 3：入
     *      韵  平声共30韵，上声29韵，去声30韵，入声17韵
     *      上平声
     *      一东 二冬 三江 四支 五微 六鱼 七虞 八齐 九佳 十灰 十一真 十二文 十三元 十四寒 十五删
     *      下平声
     *      一先 二萧 三肴 四豪 五歌 六麻 七阳 八庚 九青 十蒸 十一尤 十二侵 十三覃 十四盐 十五咸
     *      上声
     *      一董  二肿  三讲  四纸  五尾  六语  七麌  八荠  九蟹  十贿  十一轸  十二吻  十三阮  十四旱  十五潸  十六铣  十七筱  十八巧  十九皓  二十哿  二十一马  二十二养  二十三梗  二十四迥  二十五有  二十六寝  二十七感  二十八琰  二十九豏
     *      去声
     *      一送 二宋 三绛 四寘 五未 六御 七遇 八霁 九泰 十卦 十一队 十二震 十三问 十四愿 十五翰 十六谏 十七霰 十八啸 十九效 二十号 二十一个 二十二祃 二十三漾 二十四敬 二十五径 二十六宥 二十七沁 二十八勘 二十九艳 三十陷
     *      入声
     *      一屋 二沃 三觉 四质 五物 六月 七曷 八黠 九屑 十药 十一陌 十二锡 十三职 十四缉 十五合 十六叶 十七洽
     */

    /**
     *
     * 因为平仄对仗有着较为严格的规范，所以数据中存储上述数据为对比规范
     *
     * @param b
     * @return
     */

    private String getPZ(String b) {
        Iterator<wordClass> iterator = pzSource.getWordClassArrayList().iterator();
        while (iterator.hasNext()){
            wordClass word = iterator.next();
            if (word.getWord().equals(b)){
                switch (word.getPingZe()){
                    case 0:
                        return "P";
                    case 1:
                        return "Z";
                    case 2:
                        return "Z";
                    case 3:
                        return "Z";
                    default:
                        break;
                }

            }
        }
        return "";
    }

    /**
     * 搜索直接调用的方法,精确匹配主要入口方法
     *
     * @param input
     * @return
     * @throws myException
     */
    public ArrayList<tangClass> match(String input) throws myException {
        ArrayList<tangClass> resultArray = new ArrayList<tangClass>();
        if (isMutiLength(input)) {
            //变长度匹配
            logger.error("变长度匹配");
            throw new myException("变长度匹配");
        } else {
            //固定长度匹配
            //逐一匹配
            //1.减少范围，匹配诗人
            String[] authors = this.getAuthor(input);
            int authorMount = 0;
            if (authors != null)
                authorMount = authors.length;
            if (authorMount == 0 && !input.contains("&")) {
                //无作者匹配,正常匹配
                Iterator<tangClass> tangIterator = ts.getTangClassArrayList().iterator();
                //int length = this.getLenth(input);
                while (tangIterator.hasNext()) {
                    tangClass tmp = tangIterator.next();
                    if (isMatch(input, tmp.getContext())) {
                        resultArray.add(tmp);
                    }
                }

                return resultArray;


            } else {
                //存在作者约束，目前仅有或者选项，先按照作者对数据项进行筛选
                //可能有作者未收录
                Iterator<author> authorIterator = as.getAtc().iterator();
                boolean hasFound = false;
                while (authorIterator.hasNext() && !hasFound) {
                    String tmp = authorIterator.next().getName();
                    for (String author : authors) {
                        if (tmp.equals(author)) {
                            hasFound = true;
                            break;
                        }
                    }

                }

                if (!hasFound){
                    logger.error("输入作者有误或者未收录");
                    throw new myException("输入作者有误或者未收录");
                }
                else {
                    //缩小查询data范围
                    Iterator<tangClass> iterator = ts.getTangClassArrayListWithAuthor(authors).iterator();
                    //获取第二部分，纯匹配
                    String inputSearch = input.split("&")[1];

                    while (iterator.hasNext()) {
                        tangClass tmp = iterator.next();
                        if (isMatch(inputSearch, tmp.getContext())) {
                            resultArray.add(tmp);
                        }
                    }

                    return resultArray;


                }

            }


        }
    }

    /**
     *  精确匹配的方法，负责比对一句话与待匹配句子
     * @param input   待匹配诗句不含诗人名字
     * @param context 带比较诗句
     * @return
     */
    public boolean isMatch(String input, String context) throws myException {
        if (input.contains("&")) {
            throw new myException("匹配字符中含有&");
        }
        if (input == "") {
            throw new myException("空输入");
        }
        String[] tmpArray = context.split("？|，|。|\\?|,|\\n|、");
        if (tmpArray == null) {
            tmpArray = new String[1];
            tmpArray[0] = context;
        }
        int length = getLength(input);
        int count = 0;
        for (String s : tmpArray) {
            // 匹配每一个句子
            count++;

            if (getLengthFromRubbish(s) == length) {
                //继续判断
                int matched = 0;
                HashMap<Integer, String> map = getTagetString(input);
                //HashMap<Integer, String> map1 = getTagetString(s);
                HashMap<Integer, String> map1 = getTagetFromRubbish(s);

                for (int i = 0; i < length; i++) {
                    //待测试b的输入，不匹配的话matched > 0；

                    if (!judge(map.get(i + 1), map1.get(i + 1))) {
                        matched++;
                    }
                }
                if (matched > 0) {
                    continue;
                } else if (matched == 0) {
                    return true;
                }
            }
            if (count == tmpArray.length) {
                return false;
            }


        }
        //default 返回
        return false;
    }

    /**
     * 模糊匹配
     *
     * @param input
     * @param context
     * @return
     * @throws myException
     */
    public boolean possiblyMatch(String input, String context) throws myException {
        if (input.contains("&")) {
            logger.error("输入的匹配字符中含有&");
            throw new myException("匹配字符中含有&");
        }
        if (input == "") {
            logger.error("空输入，无法匹配");
            throw new myException("空输入");
        }
        String[] tmpArray = context.split("？|，|。|\\?|,|\\n|、");
        if (tmpArray == null) {
            tmpArray = new String[1];
            tmpArray[0] = context;
        }
        //TODO：从input中去掉？号
        int length = getLengthFromRubbish(context);

        int count = 0;
        for (String s : tmpArray) {
            // 匹配每一个句子
            count++;
            if (getLengthFromRubbish(s) < getLength(input)){
                return false;
            }

            HashMap<Integer, String> map = getTagetString(input);
            //HashMap<Integer, String> map1 = getTagetString(s);
            HashMap<Integer, String> map1 = getTagetFromRubbish(s);
            int[] keyWord = getKeyIntArray(input);
            int keyWordSize = keyWord.length;
            int maxInArray = getMaxInArray(keyWord);

            int[][] isFound = new int[length - maxInArray + 1][keyWordSize];
            for (int i = 0; i <= length - maxInArray; i++) {
                for (int j = 0; j < keyWordSize; j++) {
                    isFound[i][j] = 0;
                }

            }

            for (int i = 0; i <= length - maxInArray; i++) {
                for (int j = 0; j < keyWordSize; j++) {
                    if (judge(map.get(keyWord[j]), map1.get(keyWord[j] + i))) {
                        isFound[i][j] = 1;
                    }
                }
            }
            int[] matche = new int[length - maxInArray + 1];
            for (int i = 0; i < length - maxInArray; i++) {
                matche[i] = 0;
            }

            for (int i = 0; i <= length - maxInArray; i++) {
                for (int j = 0; j < keyWordSize; j++) {
                    if (isFound[i][j] == 1) {
                        matche[i]++;
                    }
                }
            }
            for (int i = 0; i <= length - maxInArray; i++) {
                if (matche[i] == keyWordSize) {
                    return true;
                }
            }


        }
        if (count == tmpArray.length) {
            return false;
        }


        return false;
    }

    /**
     *  获得一个字符串的关键字位置，关键字为需要匹配的字符约束
     * @param input
     * @return
     */

    private int[] getKeyIntArray(String input) throws myException {
        ArrayList<Integer> tmp = new ArrayList<Integer>();

        int position = 0;
        //TODO:与getTagetString整合获得数组

        for (int i = 0; i < input.length(); i++) {
            switch (input.charAt(i)) {
                case '(':
                    i = input.indexOf(')', i);
                    break;
                case '（':
                    i = input.indexOf('）',i);
                    break;
                case '[':
                    i = input.indexOf(']', i);
                    position++;
                    tmp.add(position);
                    break;
                case '{':
                    i = input.indexOf('}', i);
                    break;
                case '*':
                    position++;
                    break;
                case 'P':
                    position++;
                    break;
                case 'Z':
                    position++;
                    break;
                default:
                    position++;
                    tmp.add(position);

            }

        }
        int[] result = new int[tmp.size()];
        for (int i = 0; i < tmp.size(); i++) {
            result[i] = tmp.get(i);
        }
        return result;
    }

    public int getMaxInArray(int[] ints) {
        int tmpMax = ints[0];
        for (int i = 0; i < ints.length; i++) {
            if (tmpMax < ints[i]) {
                tmpMax = ints[i];
            }
        }
        return tmpMax;
    }

    //TODO 模糊匹配拼音匹配无法进行

    /**
     * 模糊匹配的入口方法
     * @param str
     * @return
     * @throws myException
     */
    public ArrayList<tangClass> PMatch(String str) throws myException {
        String[] authors = this.getAuthor(str);
        ArrayList<tangClass> resultArray = new ArrayList<tangClass>();
        int authorMount = 0;
        if (authors != null)
            authorMount = authors.length;
        if (authorMount == 0 && !str.contains("&")){
        Iterator<tangClass> tangIterator = ts.getTangClassArrayList().iterator();
        //int length = this.getLenth(input);
        while (tangIterator.hasNext()) {
            tangClass tmp = tangIterator.next();
            if (possiblyMatch(str, tmp.getContext())) {
                resultArray.add(tmp);
            }
        }
        return resultArray;
    }else {
            //存在作者约束，目前仅有或者选项，先按照作者对数据项进行筛选
            //可能有作者未收录
            Iterator<author> authorIterator = as.getAtc().iterator();
            boolean hasFound = false;

            while (authorIterator.hasNext() && !hasFound) {
                String tmp = authorIterator.next().getName();
                for (String author : authors) {
                    if (tmp.equals(author)) {
                        hasFound = true;
                        break;
                    }
                }

            }

            if (!hasFound){
                logger.error("输入作者有误或者未收录");
                throw new myException("输入作者有误或者未收录");
            }
            else {
                //缩小查询data范围
                Iterator<tangClass> iterator = ts.getTangClassArrayListWithAuthor(authors).iterator();
                //获取第二部分，纯匹配
                String inputSearch = str.split("&")[1];

                while (iterator.hasNext()) {
                    tangClass tmp = iterator.next();
                    if (possiblyMatch(inputSearch, tmp.getContext())) {
                        resultArray.add(tmp);
                    }
                }

                return resultArray;


            }

        }

    }

    /**
     * 对偶匹配的主要方法，目前词性文件问题导致匹配问题
     * @param str
     * @return
     * @throws myException
     */

    public ArrayList<tangClass> pairMatch(String str) throws myException{
        ArrayList<tangClass> result = new ArrayList<tangClass>();

        Iterator<tangClass> iterator = ts.getTangClassArrayList().iterator();

        HashMap<Integer,Integer> integerMap = getKindsFromRubbish(str);
        HashMap<Integer,String> stringMap = getTagetFromRubbish(str);


        while (iterator.hasNext()){
            tangClass tmp = iterator.next();
            String context = tmp.getContext();
            String[] tmpArray = context.split("？|，|。|\\?|,|\\n|、");
            for (int i = 0; i < tmpArray.length; i++) {
                //匹配目标诗句
                int length = getLengthFromRubbish(tmpArray[i]);
                int pairsWeight = 0;
                if ( length == getLengthFromRubbish(str)){
                    HashMap<Integer,Integer> integerBMap = getKindsFromRubbish(tmpArray[i]);
                    HashMap<Integer,String> stringBMap = getTagetFromRubbish(tmpArray[i]);
                    boolean[] weight = new boolean[length];
                    boolean[] pingZebooleans = new boolean[length];
                    for (int j = 0; j < length; j++) {
                        //逐字判断对偶
                        //1.词性对偶部分
                        int wordWeight = isPair(integerMap.get(j+1),integerBMap.get(j+1));
                        if (wordWeight > 0){
                           weight[j] = true;
                           pairsWeight = pairsWeight + wordWeight;
                        }else{
                           weight[j] = false;
                        }
                        if ((getPZ(stringMap.get(j+1)) == "P" && getPZ(stringBMap.get(j+1)) == "Z")||(getPZ(stringMap.get(j+1)) == "Z" && getPZ(stringBMap.get(j+1)) == "P")){
                            pingZebooleans[j] = true;
                        }else{
                            pingZebooleans[j] = false;
                        }

                    }
                    boolean passAttr = true;
                    for (int j = 0; j < length; j++) {
                        if (weight[j] == false){
                            passAttr = false;
                        }
                    }
                    boolean passPZ = true;
                    for (int j = 0; j < length; j++) {
                        if (pingZebooleans[j] == false){
                            passPZ = false;
                        }
                    }

                    if (passAttr && passPZ){
                        tangClass intoResult = new tangClass();
                        intoResult.setAuther(tmp.getAuther());
                        intoResult.setTitle(tmp.getTitle());
                        System.out.println(tmpArray[i]);
                        intoResult.setPairs(tmpArray[i]);
                        intoResult.setPairsWeight(pairsWeight);
                        result.add(intoResult);
                    }
                }else {
                    continue;
                }
            }
        }
        return result;
    }

    /**
     * 获取一个字符串的词属性
     * @param str
     * @return
     */

    public HashMap<Integer,Integer> getKindsFromRubbish(String str) {
        HashMap<Integer, Integer> result = new HashMap<>();
        int length = 0;
        for (int i = 0; i < str.length(); i++) {
            if (isChinese(str.charAt(i))) {
                String word = str.substring(i, i + 1);
                ++length;
                Iterator<wordClass> iterator = ks.getWordClassArrayList().iterator();
                boolean found = false;
                while (iterator.hasNext()){
                    wordClass tmp = iterator.next();
                    if (tmp.getWord().equals(word)){
                        result.put(length, tmp.getKind());
                        found = true;
                        break;
                    }
                }
                if (!found){
                    //System.out.println(word + " 没找到");
                    result.put(length,(1<<30)-1);
                }

            }
        }
        return result;
    }



    /**
     * 判断两个字是否在词性上对偶，int越大对偶性越强
     * @param a
     * @param b
     * @return
     * @throws myException
     */
    public int isPair(int a,int b) throws myException {
        int result = a&b;
        //System.out.println(result);
        if (result >= 0){
            return result;
        }else{
            logger.error("数字相与结果小于0");
            throw new myException("输入异常");
        }

    }

    /**
     * 获得自动生成的对偶句
     * 注释掉的是随机声称pz对字，新方法采用统计学方法，对于全部数据的字出现频率进行统计，随机生成的单字不仅照顾平仄，而且照顾出现概率
     * TODO: 下一步增加分词的功能，将分词后的诗句进行对偶生成
     * @param input
     * @return
     */
    public String getPair(String input) throws myException {
        StringBuilder result = new StringBuilder();
        HashMap<Integer,String> arraylist = getTagetFromRubbish(input);
        for (int i = 0; i < arraylist.size(); i++) {
            //get i 是从1开始计数的
            //result.append(getPZPair(arraylist.get(i+1)));
            if (i == 0){
                result.append(getPZPairByPossible(arraylist.get(i+1),""));
            }else{
                result.append(getPZPairByPossible(arraylist.get(i+1),arraylist.get(i)));
            }
        }
        return result.toString();
    }
    public void createPZList(){
        Iterator<wordClass> iterator = pzSource.getWordClassArrayList().iterator();
        pingLists = new ArrayList<wordClass>();
        zeLists = new ArrayList<wordClass>();
        while (iterator.hasNext()){
            wordClass tmp = iterator.next();
            switch (tmp.getPingZe()){
                case 0:
                    pingLists.add(tmp);
                    break;
                case 1:
                    zeLists.add(tmp);
                    break;
                case 2:
                    zeLists.add(tmp);
                    break;
                case 3:
                    zeLists.add(tmp);
                    break;
                default:
            }
        }

    }
    public String getPZPair(String word) throws myException {
        if (pingLists == null || zeLists == null){
            this.createPZList();
        }
        String wordPz = getPZ(word);
        // [0,1)
        double randomInt = Math.random();
        if (wordPz.equals("P")){
            int size = pingLists.size();
            //int size = cowl.result.size();
            int result = (int)Math.floor(randomInt*size);
            return pingLists.get(result).getWord();
        }else if (wordPz.equals("Z")){
            int size = zeLists.size();
            int result = (int)Math.floor(randomInt*size);
            return zeLists.get(result).getWord();
        }else {
            logger.error("字：" + word + " 没有平仄数据");
            throw new myException("字：" + word + " 没有平仄数据");
        }

    }

    public String getPZPairByPossible(String word,String before){

        int size = cowl.size;
        System.out.println(size);
        double randomInt = Math.random();
        Iterator<wordClass> iterator = cowl.result.iterator();
        if (before == "" || before.equals("")){
            int random = (int)Math.floor(randomInt*size);
            System.out.println("random"+random);
            int count = 0;
            while (iterator.hasNext()){
                wordClass tmp = iterator.next();
                count = count + tmp.getCount();
                if (count >= random && getPZ(word).equals(getPZ(tmp.getWord()))){
                    System.out.println("word "+tmp.getWord()+" count "+count);
                    return tmp.getWord();
                }
            }
        }else{
            int random = (int)Math.floor(randomInt*size);
            System.out.println("random"+random);
            int count = 0;
            while (iterator.hasNext()){
                wordClass tmp = iterator.next();
                count = count + tmp.getCount();
                if (count >= random && !tmp.getWord().equals(before) && getPZ(word).equals(getPZ(tmp.getWord()))){
                    System.out.println("word "+tmp.getWord()+" count "+count);
                    return tmp.getWord();
                }
            }
        }
        return null;
    }



}
