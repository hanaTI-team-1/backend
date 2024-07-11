package kr.ac.kopo.jeonse.domain.batch.vo;

import kr.ac.kopo.jeonse.domain.jeonse.domain.Jeonse;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Data
@Builder
public class CrawlingVO {
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
    private List<String> tagList;
    private String bildNm;
    private int minute;
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
    private boolean tradeCheckedByOwner;
    private Object cpLinkVO;
    private String dtlAddrYn;
    private String dtlAddr;

    public Jeonse convertToJeonse(CrawlingVO crawlingVO){
        return Jeonse.builder()
                .atclNo(crawlingVO.getAtclNo())
                .cortarNo(crawlingVO.getCortarNo())
                .atclNm(crawlingVO.getAtclNm())
                .atclStatCd(crawlingVO.getAtclStatCd())
                .rletTpCd(crawlingVO.getRletTpCd())
                .uprRletTpCd(crawlingVO.getUprRletTpCd())
                .rletTpNm(crawlingVO.getRletTpNm())
                .tradTpCd(crawlingVO.getTradTpCd())
                .tradTpNm(crawlingVO.getTradTpNm())
                .vrfcTpCd(crawlingVO.getVrfcTpCd())
                .flrInfo(crawlingVO.getFlrInfo())
                .prc(crawlingVO.getPrc())
                .rentPrc(crawlingVO.getRentPrc())
                .hanPrc(crawlingVO.getHanPrc())
                .spc1(crawlingVO.getSpc1())
                .spc2(crawlingVO.getSpc2())
                .direction(crawlingVO.getDirection())
                .atclCfmYmd(crawlingVO.getAtclCfmYmd())
                .repImgUrl(crawlingVO.getRepImgUrl())
                .repImgTpCd(crawlingVO.getRepImgTpCd())
                .repImgThumb(crawlingVO.getRepImgThumb())
                .lat(crawlingVO.getLat())
                .lng(crawlingVO.getLng())
                .atclFetrDesc(crawlingVO.getAtclFetrDesc())
                .tag1(crawlingVO.getTagList().get(0))
                .tag2(crawlingVO.getTagList().get(1))
                .tag3(crawlingVO.getTagList().get(2))
                .bildNm(crawlingVO.getBildNm())
                .minute(crawlingVO.getMinute())
                .sameAddrCnt(crawlingVO.getSameAddrCnt())
                .sameAddrDirectCnt(crawlingVO.getSameAddrDirectCnt())
                .cpid(crawlingVO.getCpid())
                .cpNm(crawlingVO.getCpNm())
                .cpCnt(crawlingVO.getCpCnt())
                .rltrNm(crawlingVO.getRltrNm())
                .sellrNm(crawlingVO.getSellrNm())
                .directTradYn(crawlingVO.getDirectTradYn())
                .minMviFee(crawlingVO.getMinMviFee())
                .maxMviFee(crawlingVO.getMaxMviFee())
                .etRoomCnt(crawlingVO.getEtRoomCnt())
                .tradePriceHan(crawlingVO.getTradePriceHan())
                .tradeRentPrice(crawlingVO.getTradeRentPrice())
                .tradeCheckedByOwner(crawlingVO.isTradeCheckedByOwner())
                .cpLinkVO(crawlingVO.getCpLinkVO().toString())
                .dtlAddrYn(crawlingVO.getDtlAddrYn())
                .dtlAddr(crawlingVO.getDtlAddr())
                .address(crawlingVO.getDtlAddr())
                .build();
    }
}