package keller.com.second_hand_car.model;

import java.io.Serializable;

public class CarInfor implements Serializable {
    private Integer cId;

    private String uId;

    private String cName;

    private String bId;

    private String pUrl;

    private String mId;

    private String cCity;

    private String cPrice;

    private String cLicensrtime;

    private String cDistance;

    private String cColor;

    private String cDes;

    private Integer cPraise;

    private Integer cBrowse;

    private Integer cStatus;

    private Integer cType;
    private Integer limit;
    private Integer start;
    private Integer count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	private static final long serialVersionUID = 1L;
    
    

    public Integer getcId() {
        return cId;
    }

    public void setcId(Integer cId) {
        this.cId = cId;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId == null ? null : uId.trim();
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName == null ? null : cName.trim();
    }

    public String getbId() {
        return bId;
    }

    public void setbId(String bId) {
        this.bId = bId == null ? null : bId.trim();
    }

    public String getpUrl() {
        return pUrl;
    }

    public void setpUrl(String pUrl) {
        this.pUrl = pUrl == null ? null : pUrl.trim();
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId == null ? null : mId.trim();
    }

    public String getcCity() {
        return cCity;
    }

    public void setcCity(String cCity) {
        this.cCity = cCity == null ? null : cCity.trim();
    }

    public String getcPrice() {
        return cPrice;
    }

    public void setcPrice(String cPrice) {
        this.cPrice = cPrice == null ? null : cPrice.trim();
    }

    public String getcLicensrtime() {
        return cLicensrtime;
    }

    public void setcLicensrtime(String cLicensrtime) {
        this.cLicensrtime = cLicensrtime == null ? null : cLicensrtime.trim();
    }

    public String getcDistance() {
        return cDistance;
    }

    public void setcDistance(String cDistance) {
        this.cDistance = cDistance == null ? null : cDistance.trim();
    }

    public String getcColor() {
        return cColor;
    }

    public void setcColor(String cColor) {
        this.cColor = cColor == null ? null : cColor.trim();
    }

    public String getcDes() {
        return cDes;
    }

    public void setcDes(String cDes) {
        this.cDes = cDes == null ? null : cDes.trim();
    }

    public Integer getcPraise() {
        return cPraise;
    }

    public void setcPraise(Integer cPraise) {
        this.cPraise = cPraise;
    }

    public Integer getcBrowse() {
        return cBrowse;
    }

    public void setcBrowse(Integer cBrowse) {
        this.cBrowse = cBrowse;
    }

    public Integer getcStatus() {
        return cStatus;
    }

    public void setcStatus(Integer cStatus) {
        this.cStatus = cStatus;
    }

    public Integer getcType() {
        return cType;
    }

    public void setcType(Integer cType) {
        this.cType = cType;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        CarInfor other = (CarInfor) that;
        return (this.getcId() == null ? other.getcId() == null : this.getcId().equals(other.getcId()))
            && (this.getuId() == null ? other.getuId() == null : this.getuId().equals(other.getuId()))
            && (this.getcName() == null ? other.getcName() == null : this.getcName().equals(other.getcName()))
            && (this.getbId() == null ? other.getbId() == null : this.getbId().equals(other.getbId()))
            && (this.getpUrl() == null ? other.getpUrl() == null : this.getpUrl().equals(other.getpUrl()))
            && (this.getmId() == null ? other.getmId() == null : this.getmId().equals(other.getmId()))
            && (this.getcCity() == null ? other.getcCity() == null : this.getcCity().equals(other.getcCity()))
            && (this.getcPrice() == null ? other.getcPrice() == null : this.getcPrice().equals(other.getcPrice()))
            && (this.getcLicensrtime() == null ? other.getcLicensrtime() == null : this.getcLicensrtime().equals(other.getcLicensrtime()))
            && (this.getcDistance() == null ? other.getcDistance() == null : this.getcDistance().equals(other.getcDistance()))
            && (this.getcColor() == null ? other.getcColor() == null : this.getcColor().equals(other.getcColor()))
            && (this.getcDes() == null ? other.getcDes() == null : this.getcDes().equals(other.getcDes()))
            && (this.getcPraise() == null ? other.getcPraise() == null : this.getcPraise().equals(other.getcPraise()))
            && (this.getcBrowse() == null ? other.getcBrowse() == null : this.getcBrowse().equals(other.getcBrowse()))
            && (this.getcStatus() == null ? other.getcStatus() == null : this.getcStatus().equals(other.getcStatus()))
            && (this.getcType() == null ? other.getcType() == null : this.getcType().equals(other.getcType()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getcId() == null) ? 0 : getcId().hashCode());
        result = prime * result + ((getuId() == null) ? 0 : getuId().hashCode());
        result = prime * result + ((getcName() == null) ? 0 : getcName().hashCode());
        result = prime * result + ((getbId() == null) ? 0 : getbId().hashCode());
        result = prime * result + ((getpUrl() == null) ? 0 : getpUrl().hashCode());
        result = prime * result + ((getmId() == null) ? 0 : getmId().hashCode());
        result = prime * result + ((getcCity() == null) ? 0 : getcCity().hashCode());
        result = prime * result + ((getcPrice() == null) ? 0 : getcPrice().hashCode());
        result = prime * result + ((getcLicensrtime() == null) ? 0 : getcLicensrtime().hashCode());
        result = prime * result + ((getcDistance() == null) ? 0 : getcDistance().hashCode());
        result = prime * result + ((getcColor() == null) ? 0 : getcColor().hashCode());
        result = prime * result + ((getcDes() == null) ? 0 : getcDes().hashCode());
        result = prime * result + ((getcPraise() == null) ? 0 : getcPraise().hashCode());
        result = prime * result + ((getcBrowse() == null) ? 0 : getcBrowse().hashCode());
        result = prime * result + ((getcStatus() == null) ? 0 : getcStatus().hashCode());
        result = prime * result + ((getcType() == null) ? 0 : getcType().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", cId=").append(cId);
        sb.append(", uId=").append(uId);
        sb.append(", cName=").append(cName);
        sb.append(", bId=").append(bId);
        sb.append(", pUrl=").append(pUrl);
        sb.append(", mId=").append(mId);
        sb.append(", cCity=").append(cCity);
        sb.append(", cPrice=").append(cPrice);
        sb.append(", cLicensrtime=").append(cLicensrtime);
        sb.append(", cDistance=").append(cDistance);
        sb.append(", cColor=").append(cColor);
        sb.append(", cDes=").append(cDes);
        sb.append(", cPraise=").append(cPraise);
        sb.append(", cBrowse=").append(cBrowse);
        sb.append(", cStatus=").append(cStatus);
        sb.append(", cType=").append(cType);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}