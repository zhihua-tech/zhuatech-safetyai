/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.safetyai.service;
import jakarta.validation.constraints.*; import org.springframework.stereotype.Service; import java.util.*;
@Service public class SafetyRiskService {
 public Result assess(Request q){int score=0;List<String> controls=new ArrayList<>();
  score+=Math.min(30,q.hazardCount()*5);score+=Math.min(30,q.highRiskOperations()*15);score+=Math.min(20,q.weatherSeverity()*5);
  if(q.trainingCoveragePercent()<90){score+=15;controls.add("补齐入场培训和高风险作业交底");}
  if(q.protectiveEquipmentCompliancePercent()<95){score+=20;controls.add("复核个人防护用品佩戴与现场监护");}
  if(q.personnelCount()>30){score+=10;controls.add("增加现场监护人员并执行分区作业");}
  if(q.highRiskOperations()>0)controls.add("动火、受限空间或高处作业须二次确认");
  score=Math.min(100,score);String decision=score>=70?"STOP":score>=40?"REVIEW":"PASS";String level=score>=70?"CRITICAL":score>=40?"WATCH":"CONTROLLED";
  if(controls.isEmpty())controls.add("维持班前检查与过程巡检");
  return new Result(q.workPermitNo(),score,level,decision,decision.equals("STOP")?"暂停开工，完成隐患整改并由安全负责人复核":decision.equals("REVIEW")?"补充控制措施后重新审批":"允许按已批准方案作业",controls);}
 public record Request(@NotBlank String workPermitNo,@Min(0) int hazardCount,@Min(0) int highRiskOperations,@Min(1) int personnelCount,@Min(0) @Max(4) int weatherSeverity,@Min(0) @Max(100) int trainingCoveragePercent,@Min(0) @Max(100) int protectiveEquipmentCompliancePercent){}
 public record Result(String workPermitNo,int riskScore,String riskLevel,String releaseDecision,String recommendation,List<String> requiredControls){}
}
