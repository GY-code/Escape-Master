package com.team6.escapemaster_server.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KnnPlusKernel {
     public static final double PI = 3.1415926;

    /*描述：得到最邻近的K个点
      参数：所有封装好的标签点
          目标点
          K值*/
    public ArrayList<RssObj> getPreEstimatePoints(List<RssObj> fingerPrintMap, RssObj targetPoint, int estimateNum){
        ArrayList<RssObj> list1 = new ArrayList<RssObj>(estimateNum);//K个曼哈顿距离最小的标签点
        for(RssObj rssObj : fingerPrintMap){
            int lenA = rssObj.signalAVGs.size();
            int lenB = targetPoint.signalAVGs.size();
            int count = lenA<=lenB? lenA : lenB;
            for(int i = 0;i<count;i++){
                rssObj.distance+=Math.abs(rssObj.signalAVGs.get(i)-targetPoint.signalAVGs.get(i));
            }
            rssObj.distance/=count;
            list1.add(rssObj);
        }
        Collections.sort(list1);
        ArrayList<RssObj> list2 = new ArrayList<>();
        for(int i=0;i<estimateNum;i++){
            list2.add(list1.get(i));
        }
        return list2;
    }

    //通过权重计算得到估计点
    public Position getFinalPosition(RssObj targetPoint,ArrayList<RssObj> list){

        double estimateX = 0.0;
        double estimateY = 0.0;
        double sumWeigh = 0.0;
        for(RssObj rssObj : list)
        {
            double weight = NormalP(targetPoint,rssObj);
            sumWeigh += weight;
            estimateX += rssObj.pos.x * weight;
            estimateY += rssObj.pos.y * weight;
        }
        Position estimatePos = new Position();
        estimatePos.x = estimateX / sumWeigh;
        estimatePos.y = estimateY / sumWeigh;
        return estimatePos;
    }

    //计算权重
    public double NormalP(RssObj targetPoint, RssObj labelPoint){
        int num = labelPoint.signalAVGs.size();
        double sum=0.0;
        for(int i=0;i<num;i++){
            double avg=labelPoint.signalAVGs.get(i);
            double var = labelPoint.signalVARs.get(i);
            sum+=1/(Math.sqrt(2*PI)*var)*Math.exp(-((Math.pow(targetPoint.signalAVGs.get(i)-avg,2))/(2*var*var)));
        }
        return sum/num;
    }

    public static String getPosByKNK(ArrayList<RssObj> list,RssObj tar,int K) {
        KnnPlusKernel kpk = new KnnPlusKernel();
        ArrayList<RssObj> pointsList = kpk.getPreEstimatePoints(list,tar,K);
        return kpk.getFinalPosition(tar,pointsList).toString();
    }
}
