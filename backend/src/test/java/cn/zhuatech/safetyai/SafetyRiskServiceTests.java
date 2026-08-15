/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.safetyai;
import cn.zhuatech.safetyai.service.SafetyRiskService; import org.junit.jupiter.api.Test; import static org.assertj.core.api.Assertions.assertThat;
class SafetyRiskServiceTests{private final SafetyRiskService service=new SafetyRiskService();@Test void stopsUnsafePermit(){var r=service.assess(new SafetyRiskService.Request("WP-260815-09",8,2,42,3,72,81));assertThat(r.releaseDecision()).isEqualTo("STOP");assertThat(r.requiredControls()).hasSizeGreaterThanOrEqualTo(3);}@Test void passesControlledPermit(){var r=service.assess(new SafetyRiskService.Request("WP-260815-21",1,0,8,0,100,100));assertThat(r.releaseDecision()).isEqualTo("PASS");assertThat(r.riskLevel()).isEqualTo("CONTROLLED");}}
