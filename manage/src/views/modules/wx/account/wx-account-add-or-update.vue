<template>
  <el-dialog
    title="新增/修改"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <div class="padding text-gray">请先到野火开放平台创建频道，<a href="https://github.com/wildfirechat/open-platform">开发平台项目</a></div>
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="100px">
      <el-form-item label="频道名称" prop="name">
      <el-input v-model="dataForm.name" placeholder="频道名称"></el-input>
    </el-form-item>
    <el-form-item label="imurl" prop="imurl">
      <el-input v-model="dataForm.imurl" placeholder="IM服务地址"></el-input>
    </el-form-item>
    <el-form-item label="appid" prop="appid">
      <el-input v-model="dataForm.appid" placeholder="appid" maxlength="36"></el-input>
    </el-form-item>
    <el-form-item label="appsecret" prop="secret">
      <el-input v-model="dataForm.secret" placeholder="appsecret" maxlength="36"></el-input>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import { mapState } from 'vuex'
  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          appid: '',
          name: '',
          imurl:'',
          type:'1',
          verified:true,
          secret: ''
        },
        dataRule: {
          name: [
            { required: true, message: '频道名称不能为空', trigger: 'blur' }
          ],
          appid: [
            { required: true, message: 'appid不能为空', trigger: 'blur' }
          ],
          imurl: [
            { required: true, message: 'im服务地址不能为空', trigger: 'blur' }
          ],
          secret: [
            { required: true, message: 'appsecret不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    computed: mapState({
        ACCOUNT_TYPES: state=>state.wxAccount.ACCOUNT_TYPES
    }),
    methods: {
      init (item) {
        this.visible = true
        if(item && item.appid){
            this.dataForm = item
            this.dataForm.type = item.type+''
        }else{
            this.$nextTick(() => {
                this.$refs['dataForm'].resetFields()
            })
        }

      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/manage/wxAccount/save`),
              method: 'post',
           data: this.$http.adornData(this.dataForm)
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      }
    }
  }
</script>
