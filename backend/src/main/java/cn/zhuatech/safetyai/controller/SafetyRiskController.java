/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.safetyai.controller;
import cn.zhuatech.safetyai.common.ApiResponse; import cn.zhuatech.safetyai.service.SafetyRiskService; import jakarta.validation.Valid; import org.springframework.security.access.prepost.PreAuthorize; import org.springframework.web.bind.annotation.*;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@RestController @RequestMapping("/api/ai/safety") @PreAuthorize("hasAnyRole('DOMAIN_USER','DOMAIN_OPERATOR','ADMIN')") public class SafetyRiskController{private final SafetyRiskService service;/**
                                                                                                                                                                                                  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                  */
public SafetyRiskController(SafetyRiskService service){this.service=service;}/**
                                                                                                                                                                                                                                                                               * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                                                                                               */
@PostMapping("/assess") public ApiResponse<SafetyRiskService.Result> assess(@Valid @RequestBody SafetyRiskService.Request request){return ApiResponse.ok("作业安全风险评估完成",service.assess(request));}}
