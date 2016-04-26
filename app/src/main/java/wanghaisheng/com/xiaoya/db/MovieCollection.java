package wanghaisheng.com.xiaoya.db;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "MOVIE_COLLECTION".
 */
public class MovieCollection {

    private Long id;
    private String showInfo;
    private String scm;
    private String dir;
    private String star;
    private String cat;
    private Integer wish;
    private Boolean value3d;
    private String nm;
    private Boolean imax;
    private Integer snum;
    private String sc;
    private String ver;
    private String rt;
    private String img;
    private String dur;
    private Boolean is_collected;

    public MovieCollection() {
    }

    public MovieCollection(Long id) {
        this.id = id;
    }

    public MovieCollection(Long id, String showInfo, String scm, String dir, String star, String cat, Integer wish, Boolean value3d, String nm, Boolean imax, Integer snum, String sc, String ver, String rt, String img, String dur, Boolean is_collected) {
        this.id = id;
        this.showInfo = showInfo;
        this.scm = scm;
        this.dir = dir;
        this.star = star;
        this.cat = cat;
        this.wish = wish;
        this.value3d = value3d;
        this.nm = nm;
        this.imax = imax;
        this.snum = snum;
        this.sc = sc;
        this.ver = ver;
        this.rt = rt;
        this.img = img;
        this.dur = dur;
        this.is_collected = is_collected;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShowInfo() {
        return showInfo;
    }

    public void setShowInfo(String showInfo) {
        this.showInfo = showInfo;
    }

    public String getScm() {
        return scm;
    }

    public void setScm(String scm) {
        this.scm = scm;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public Integer getWish() {
        return wish;
    }

    public void setWish(Integer wish) {
        this.wish = wish;
    }

    public Boolean getValue3d() {
        return value3d;
    }

    public void setValue3d(Boolean value3d) {
        this.value3d = value3d;
    }

    public String getNm() {
        return nm;
    }

    public void setNm(String nm) {
        this.nm = nm;
    }

    public Boolean getImax() {
        return imax;
    }

    public void setImax(Boolean imax) {
        this.imax = imax;
    }

    public Integer getSnum() {
        return snum;
    }

    public void setSnum(Integer snum) {
        this.snum = snum;
    }

    public String getSc() {
        return sc;
    }

    public void setSc(String sc) {
        this.sc = sc;
    }

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public String getRt() {
        return rt;
    }

    public void setRt(String rt) {
        this.rt = rt;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDur() {
        return dur;
    }

    public void setDur(String dur) {
        this.dur = dur;
    }

    public Boolean getIs_collected() {
        return is_collected;
    }

    public void setIs_collected(Boolean is_collected) {
        this.is_collected = is_collected;
    }

}
