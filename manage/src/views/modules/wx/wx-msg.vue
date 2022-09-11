<template>
    <div class="mod-config">
        <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
            <el-form-item>
                <el-select v-model="dataForm.startTime" placeholder="时间">
                    <el-option v-for="(name,key) in timeSelections" :key="key" :value="name" :label="key"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-select v-model="dataForm.msgTypes" placeholder="消息类型">
                    <el-option value="" label="不限类型"></el-option>
                    <el-option value="text,image,voice,shortvideo,video,news,music,location,link" label="消息"></el-option>
                    <el-option value="event,transfer_customer_service" label="事件"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button @click="getDataList()">查询</el-button>
                <el-button @click="showSendMsgDialog = true">发送消息</el-button>
            </el-form-item>
        </el-form>
        <div class="text-gray">
            24小时内消息可回复。此后台展示消息有一分钟左右延迟，如需畅聊请使用
            <a href="https://mpkf.weixin.qq.com/" target="_blank">公众平台客服</a>
        </div>
        <div v-loading="dataListLoading">
            <div class="msg-item" v-for="(msg,index) in  dataList" :key="index">
                <div class="avatar"><el-avatar shape="square" :size="60" :src="getUserInfo(msg.openid).headimgurl"></el-avatar></div>
                <div class="item-content">
                    <div class="flex justify-between margin-bottom">
                        <div class="text-cut">{{getUserInfo(msg.openid).nickname || '--'}}</div>
                        <div>{{$moment(msg.createTime).calendar()}}</div>
                        <div class="reply-btn">
                            <div v-if="canReply(msg.createTime)" @click="replyHandle(msg.openid)" class="el-icon-s-promotion">回复</div>
                        </div>
                    </div>
                    <wx-msg-preview :msg="msg" singleLine></wx-msg-preview>
                </div>
            </div>
        </div>
        <el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" :current-page="pageIndex" :page-sizes="[10, 20, 50, 100]" :page-size="pageSize" :total="totalCount" layout="total, sizes, prev, pager, next, jumper">
        </el-pagination>
        <!-- 弹窗, 消息回复 -->
        <wx-msg-reply ref="wxMsgReply" @success="onReplyed"></wx-msg-reply>
        <el-dialog
            title="发送文本消息"
            :close-on-click-modal="false"
            :visible.sync="showSendMsgDialog">
            <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="sendTextMessageContent" label-width="100px">
                <el-form-item label="消息内容" prop="toSendTextMessageContent">
                    <el-input v-model="dataForm.toSendTextMessageContent" placeholder="文本消息内容"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="showSendMsgDialog = false">取消</el-button>
                <el-button type="primary" @click="sendTextMessageContent">发送</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import { mapState } from 'vuex'
const TIME_FORMAT = 'YYYY/MM/DD hh:mm:ss'
export default {
    data() {
        return {
            timeSelections:{
                '近24小时':this.$moment().subtract(1, 'days').format(TIME_FORMAT),
                '近3天': this.$moment().subtract(3, 'days').format(TIME_FORMAT),
                '近7天': this.$moment().subtract(7, 'days').format(TIME_FORMAT),
                '近30天': this.$moment().subtract(30, 'days').format(TIME_FORMAT),
            },
            dataForm: {
                startTime: this.$moment().subtract(1, 'days').format(TIME_FORMAT),
                msgTypes: '',
                toSendTextMessageContent: '',
            },
            dataRule: {
                toSendTextMessageContent: [
                    {required: true, message: "消息内容不能为空", trigger: "blur"}
                ]
            },
            dataList: [],
            userDataList:[],
            pageIndex: 1,
            pageSize: 20,
            totalCount: 0,
            dataListLoading: false,
            dataListSelections: [],
            showSendMsgDialog: false,
        }
    },
    components: {
        WxMsgReply:()=>import('./wx-msg-reply'),
        WxMsgPreview:()=>import('@/components/wx-msg-preview')
    },
    activated() {
        this.getDataList()
    },
    computed: mapState({
        selectedAppid:state=>state.wxAccount.selectedAppid
    }),
    methods: {
        // 获取数据列表
        getDataList() {
            this.dataListLoading = true
            this.$http({
                url: this.$http.adornUrl('/manage/wxMsg/list'),
                method: 'get',
                params: this.$http.adornParams({
                    'page': this.pageIndex,
                    'limit': this.pageSize,
                    'msgTypes': this.dataForm.msgTypes,
                    'startTime':this.dataForm.startTime,
                    'sidx': 'create_time',
                    'order': 'desc'
                })
            }).then(({ data }) => {
                if (data && data.code === 200) {
                    this.dataList = data.page.list
                    this.totalCount = data.page.totalCount
                    this.refreshUserList(this.dataList)
                } else {
                    this.dataList = []
                    this.totalCount = 0
                }
                this.dataListLoading = false
            })
        },
        refreshUserList(msgList){
            let openidList=msgList.map(msg=>msg.openid).filter(openid=>!this.userDataList.some(u=>u.openid==openid))
            if(!openidList.length)return
            openidList = Array.from(new Set(openidList))//去重
            this.$http({
                url: this.$http.adornUrl('/manage/wxUser/listByIds'),
                method: 'post',
                data: this.$http.adornParams(openidList,false)
            }).then(({ data }) => {
                if (data && data.code === 200) {
                    this.userDataList = this.userDataList.concat(data.data)
                }
            })
        },
        getUserInfo(openid){
            return this.userDataList.find(u=>u.openid==openid) || {nickname:'--',headimgurl:''}
        },
        // 是否可回复，24小时内可回复
        canReply(time){
            return true
        },
        // 每页数
        sizeChangeHandle(val) {
            this.pageSize = val
            this.pageIndex = 1
            this.getDataList()
        },
        // 当前页
        currentChangeHandle(val) {
            this.pageIndex = val
            this.getDataList()
        },
        // 多选
        selectionChangeHandle(val) {
            this.dataListSelections = val
        },
        // 回复消息
        replyHandle(openid) {
            this.$nextTick(() => {
                this.$refs.wxMsgReply.init(openid)
            })
        },
        onReplyed(replyMsg){
            this.dataList.unshift({
                openid : replyMsg.openid,
                msgType : replyMsg.replyType,
                detail : {
                    content : replyMsg.replyContent
                },
                inOut : 1,
                createTime : new Date()
            })
        },
        sendTextMessageContent() {

            this.$refs['dataForm'].validate((valid) => {
                if (valid) {
                    this.$http({
                        url: this.$http.adornUrl(`/manage/wxMsg/send/${this.selectedAppid}`),
                        method: 'post',
                        data: this.dataForm.toSendTextMessageContent,
                    }).then(({ data }) => {
                        if (data && data.code === 200) {
                            this.$message({
                                message: '操作成功',
                                type: 'success',
                                duration: 1500,
                                onClose: () => this.getDataList()
                            })
                        } else {
                            this.$message.error(data.msg)
                        }
                    })
                }
            });
        }
    }
}
</script>
<style scoped>
.msg-item{
    border: 1px solid #DCDFE6;
    display: flex;
    justify-content: flex-start;
    align-items: top;
    margin-top: 20px;
    padding: 10px 20px;
}
.avatar{
    flex: 0;
    display: inline-block;
    min-width: 60px;
    margin-right: 20px;
}
.item-content{
    flex: 1;
    line-height: 20px;
    max-width: 100%;
    overflow: hidden;
}
.reply-btn{
    width: 50px;
}
</style>
