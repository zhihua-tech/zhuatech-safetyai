/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.safetyai.config;
import cn.zhuatech.safetyai.model.*; import cn.zhuatech.safetyai.repository.*; import org.springframework.boot.CommandLineRunner; import org.springframework.context.annotation.*; import org.springframework.security.crypto.password.PasswordEncoder; import java.time.LocalDate; import java.util.List;
@Configuration public class DataInitializer {
 @Bean CommandLineRunner seed(OperatingUnitRepository units,WorkRecordRepository orders,ResourceRegisterRepository resources,ReviewRecordRepository reviews,UserRepository users,PasswordEncoder encoder){return args->{if(units.count()>0)return;
 var u1=units.save(new OperatingUnit("HSE-CENTER","集团安环中心","运营中心",180));var u2=units.save(new OperatingUnit("PLANT-SAFETY","华东工厂安全组","制造中心",120));var u3=units.save(new OperatingUnit("CONTRACTOR","承包商管理组","工程中心",96));
 var t1=orders.save(new WorkRecord("SA-260815-018","WORK-HOT-017","罐区动火作业风险评估",u1,24,16,1,LocalDate.now().plusDays(1),WorkRecord.Status.RUNNING,"作业票+气体检测"));
 var t2=orders.save(new WorkRecord("SA-260815-021","WORK-HIGH-032","仓库高处作业评估",u2,18,8,0,LocalDate.now().plusDays(2),WorkRecord.Status.RUNNING,"作业票+视频"));
 var t3=orders.save(new WorkRecord("SA-260815-026","WORK-LIFT-008","大型吊装作业评估",u3,12,0,0,LocalDate.now().plusDays(3),WorkRecord.Status.RELEASED,"方案+人员资质"));
 var t4=orders.save(new WorkRecord("SA-260814-015","WORK-SPACE-011","受限空间作业复盘",u1,20,20,1,LocalDate.now(),WorkRecord.Status.COMPLETED,"检测+监护记录"));
 resources.saveAll(List.of(new ResourceRegister("PERMIT-01","电子作业票平台",u1,ResourceRegister.Status.RUNNING,99),new ResourceRegister("MODEL-02","作业风险评估模型",u2,ResourceRegister.Status.IDLE,94),new ResourceRegister("VIDEO-03","现场视频连接器",u2,ResourceRegister.Status.RUNNING,92),new ResourceRegister("ALARM-04","安全告警服务",u3,ResourceRegister.Status.ALARM,87)));
 reviews.saveAll(List.of(new ReviewRecord("RV-260804-032",t1,"人工复核",6,0,ReviewRecord.Result.PASSED,"程越"),new ReviewRecord("RV-260804-011",t2,"质量检查",3,0,ReviewRecord.Result.PASSED,"许知"),new ReviewRecord("RV-260803-018",t4,"结果抽查",5,1,ReviewRecord.Result.FAILED,"程越"),new ReviewRecord("RV-260804-003",t3,"上线确认",4,0,ReviewRecord.Result.PENDING,"许知")));
 String demo=encoder.encode("Demo@2026");users.saveAll(List.of(new UserAccount("operator",demo,"许知",UserAccount.Role.DOMAIN_USER,"SEARCH-OPS"),new UserAccount("planner",demo,"程越",UserAccount.Role.DOMAIN_OPERATOR,null),new UserAccount("quality",demo,"顾清",UserAccount.Role.QUALITY,null),new UserAccount("admin",encoder.encode("ZhuaTech@2026"),"系统管理员",UserAccount.Role.ADMIN,null)));};}
}
