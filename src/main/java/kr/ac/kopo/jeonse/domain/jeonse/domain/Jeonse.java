package kr.ac.kopo.jeonse.domain.jeonse.domain;

import lombok.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Jeonse {
    private String atclNo;
    private String cortarNo;
    private String atclNm;
    private String atclStatCd;
    private String rletTpCd;
    private String uprRletTpCd;
    private String rletTpNm;
    private String tradTpCd;
    private String tradTpNm;
    private String vrfcTpCd;
    private String flrInfo;
    private int prc;
    private int rentPrc;
    private String hanPrc;
    private String spc1;
    private String spc2;
    private String direction;
    private String atclCfmYmd;
    private String repImgUrl;
    private String repImgTpCd;
    private String repImgThumb;
    private double lat;
    private double lng;
    private String atclFetrDesc;
    private String tag1;
    private String tag2;
    private String tag3;
    private String bildNm;
    private String minute;
    private int sameAddrCnt;
    private int sameAddrDirectCnt;
    private String cpid;
    private String cpNm;
    private int cpCnt;
    private String rltrNm;
    private String sellrNm;
    private String directTradYn;
    private int minMviFee;
    private int maxMviFee;
    private int etRoomCnt;
    private String tradePriceHan;
    private int tradeRentPrice;
    private String tradeCheckedByOwner;
    private String cpLinkVO;
    private String dtlAddrYn;
    private String dtlAddr;
    private String address;
}
