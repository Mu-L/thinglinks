export interface VideoMediaServerPageQuery {
  appId?: string; // 应用ID
  ip?: string; // 服务器IP地址
  hookIp?: string; // hook使用的IP（zlm访问客户端使用的IP）
  sdpIp?: string; // SDP IP地址
  streamIp?: string; // 流IP地址
  httpPort?: number; // HTTP端口
  httpSslPort?: number; // HTTPS端口
  rtmpPort?: number; // RTMP端口
  rtmpSslPort?: number; // RTMP SSL端口
  rtpProxyPort?: number; // RTP代理端口（单端口模式）
  rtspPort?: number; // RTSP端口
  rtspSslPort?: number; // RTSP SSL端口
  flvPort?: number; // FLV端口
  flvSslPort?: number; // FLV SSL端口
  wsFlvPort?: number; // WebSocket FLV端口
  wsFlvSslPort?: number; // WebSocket FLV SSL端口
  autoConfig?: boolean; // 是否开启自动配置ZLM
  secret?: string; // ZLM鉴权参数
  type?: string; // 类型（zlm/abl）
  rtpEnable?: boolean; // 是否启用多端口模式
  rtpPortRange?: string; // 多端口RTP收流端口范围
  sendRtpPortRange?: string; // RTP发流端口范围
  recordAssistPort?: number; // 录制辅助服务端口
  defaultServer?: boolean; // 是否是默认ZLM服务器
  hookAliveInterval?: number; // keepalive hook触发间隔，单位秒
  recordPath?: string; // 录像存储路径
  recordDay?: number; // 录像存储时长（天）
  transcodeSuffix?: string; // 转码的前缀
  remark?: string; // 备注
  createdOrgId?: string; // 创建人组织
  extendParams?: string; // 扩展参数
  name?: string; // 名称
  mediaIdentification?: string; // 媒体识别码
  createdTime?: string; // 创建时间
  id?: string; // 数据ID
}

export interface VideoMediaServerSaveVO {
  appId?: string; // 应用ID
  ip?: string; // 服务器IP地址
  hookIp?: string; // hook使用的IP（zlm访问客户端使用的IP）
  sdpIp?: string; // SDP IP地址
  streamIp?: string; // 流IP地址
  httpPort?: number; // HTTP端口
  httpSslPort?: number; // HTTPS端口
  rtmpPort?: number; // RTMP端口
  rtmpSslPort?: number; // RTMP SSL端口
  rtpProxyPort?: number; // RTP代理端口（单端口模式）
  rtspPort?: number; // RTSP端口
  rtspSslPort?: number; // RTSP SSL端口
  flvPort?: number; // FLV端口
  flvSslPort?: number; // FLV SSL端口
  wsFlvPort?: number; // WebSocket FLV端口
  wsFlvSslPort?: number; // WebSocket FLV SSL端口
  autoConfig?: boolean; // 是否开启自动配置ZLM
  secret?: string; // ZLM鉴权参数
  type?: string; // 类型（zlm/abl）
  rtpEnable?: boolean; // 是否启用多端口模式
  rtpPortRange?: string; // 多端口RTP收流端口范围
  sendRtpPortRange?: string; // RTP发流端口范围
  recordAssistPort?: number; // 录制辅助服务端口
  defaultServer?: boolean; // 是否是默认ZLM服务器
  hookAliveInterval?: number; // keepalive hook触发间隔，单位秒
  recordPath?: string; // 录像存储路径
  recordDay?: number; // 录像存储时长（天）
  transcodeSuffix?: string; // 转码的前缀
  remark?: string; // 备注
  createdOrgId?: string; // 创建人组织
  extendParams?: string; // 扩展参数
  name?: string; // 名称
}

export interface VideoMediaServerUpdateVO {
  id: string;
  appId?: string; // 应用ID
  ip?: string; // 服务器IP地址
  hookIp?: string; // hook使用的IP（zlm访问客户端使用的IP）
  sdpIp?: string; // SDP IP地址
  streamIp?: string; // 流IP地址
  httpPort?: number; // HTTP端口
  httpSslPort?: number; // HTTPS端口
  rtmpPort?: number; // RTMP端口
  rtmpSslPort?: number; // RTMP SSL端口
  rtpProxyPort?: number; // RTP代理端口（单端口模式）
  rtspPort?: number; // RTSP端口
  rtspSslPort?: number; // RTSP SSL端口
  flvPort?: number; // FLV端口
  flvSslPort?: number; // FLV SSL端口
  wsFlvPort?: number; // WebSocket FLV端口
  wsFlvSslPort?: number; // WebSocket FLV SSL端口
  autoConfig?: boolean; // 是否开启自动配置ZLM
  secret?: string; // ZLM鉴权参数
  type?: string; // 类型（zlm/abl）
  rtpEnable?: boolean; // 是否启用多端口模式
  rtpPortRange?: string; // 多端口RTP收流端口范围
  sendRtpPortRange?: string; // RTP发流端口范围
  recordAssistPort?: number; // 录制辅助服务端口
  defaultServer?: boolean; // 是否是默认ZLM服务器
  hookAliveInterval?: number; // keepalive hook触发间隔，单位秒
  recordPath?: string; // 录像存储路径
  recordDay?: number; // 录像存储时长（天）
  transcodeSuffix?: string; // 转码的前缀
  remark?: string; // 备注
  createdOrgId?: string; // 创建人组织
  extendParams?: string; // 扩展参数
  name?: string; // 名称
  mediaIdentification?: string; // 媒体标识
}

export interface VideoMediaServerResultVO {
  echoMap?: any;
  id?: string; // 唯一标识符
  createdTime?: string; // 创建时间
  createdBy?: string; // 创建人
  updatedTime?: string; // 最后修改时间
  updatedBy?: string; // 最后修改人
  appId?: string; // 应用ID
  ip?: string; // 服务器IP地址
  hookIp?: string; // hook使用的IP（zlm访问客户端使用的IP）
  sdpIp?: string; // SDP IP地址
  streamIp?: string; // 流IP地址
  httpPort?: number; // HTTP端口
  httpSslPort?: number; // HTTPS端口
  rtmpPort?: number; // RTMP端口
  rtmpSslPort?: number; // RTMP SSL端口
  rtpProxyPort?: number; // RTP代理端口（单端口模式）
  rtspPort?: number; // RTSP端口
  rtspSslPort?: number; // RTSP SSL端口
  flvPort?: number; // FLV端口
  flvSslPort?: number; // FLV SSL端口
  wsFlvPort?: number; // WebSocket FLV端口
  wsFlvSslPort?: number; // WebSocket FLV SSL端口
  autoConfig?: boolean; // 是否开启自动配置ZLM
  secret?: string; // ZLM鉴权参数
  type?: string; // 类型（zlm/abl）
  rtpEnable?: boolean; // 是否启用多端口模式
  rtpPortRange?: string; // 多端口RTP收流端口范围
  sendRtpPortRange?: string; // RTP发流端口范围
  recordAssistPort?: number; // 录制辅助服务端口
  defaultServer?: boolean; // 是否是默认ZLM服务器
  hookAliveInterval?: number; // keepalive hook触发间隔，单位秒
  recordPath?: string; // 录像存储路径
  recordDay?: number; // 录像存储时长（天）
  transcodeSuffix?: string; // 转码的前缀
  remark?: string; // 备注
  createdOrgId?: string; // 创建人组织
  extendParams?: string; // 扩展参数
  name?: string; // 名称
}
