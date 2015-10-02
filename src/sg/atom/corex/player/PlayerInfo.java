package sg.atom.corex.player;

import com.jme3.app.Application;
import com.jme3.asset.AssetManager;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import java.io.IOException;
import java.util.Date;
import org.apache.commons.configuration.Configuration;
import sg.atom.core.lifecycle.IGameCycle;
import sg.atom.core.lifecycle.ManagableObject;

/**
 *
 * @author CuongNguyen
 */
public class PlayerInfo implements ManagableObject, Savable {

    int num;
    String name;
    String avatar;
    int point;
    int rank;
    int score;
    int gold;
    int coin;
    int money;
    int status;
    int playCount = 0;
    private String AVATAR_DIR = "Interface/Images/Ingame/Portrait/";

    //Infomations & Social------------------------------------------------------
    String realName;
    int sex;
    String fbAccount;
    String fbPassword;
    String fbUID;
    String gpAccount;
    String gpPassword;
    String gpUID;
    String email;
    String email2;
    int age;
    Date birthDate;
    int country;
    String address;
    String phoneNumber;

    public PlayerInfo(int num, String name, String avatar) {
        this.num = num;
        this.name = name;
        this.avatar = avatar;
    }

    public void init(Application app) {
    }

    public void initManagers(IGameCycle... managers) {
    }

    public void load(AssetManager assetManager) {
    }

    public void config(Configuration configuration) {
    }

    public void update(float tpf) {
    }

    public void finish() {
    }

    public void write(JmeExporter ex) throws IOException {
    }

    public void read(JmeImporter im) throws IOException {
    }

    public void resetAttributes() {

    }

    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }

    public int getPlayCount() {
        return playCount;
    }

    public String getName() {
        return name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getFbAccount() {
        return fbAccount;
    }

    public void setFbAccount(String fbAccount) {
        this.fbAccount = fbAccount;
    }

    public String getFbPassword() {
        return fbPassword;
    }

    public void setFbPassword(String fbPassword) {
        this.fbPassword = fbPassword;
    }

    public String getFbUID() {
        return fbUID;
    }

    public void setFbUID(String fbUID) {
        this.fbUID = fbUID;
    }

    public String getGpAccount() {
        return gpAccount;
    }

    public void setGpAccount(String gpAccount) {
        this.gpAccount = gpAccount;
    }

    public String getGpPassword() {
        return gpPassword;
    }

    public void setGpPassword(String gpPassword) {
        this.gpPassword = gpPassword;
    }

    public String getGpUID() {
        return gpUID;
    }

    public void setGpUID(String gpUID) {
        this.gpUID = gpUID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public int getCountry() {
        return country;
    }

    public void setCountry(int country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
