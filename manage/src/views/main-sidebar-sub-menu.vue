<template>
    <el-submenu v-if="menu.list && menu.list.length >= 1" :index="menu.menuId + ''" :popper-class="'site-sidebar--' + sidebarLayoutSkin + '-popper'">
        <template slot="title">
            <i class="site-sidebar__menu-icon" :class="menu.icon"></i>
            <!-- <icon-svg :name="menu.icon || ''" class="site-sidebar__menu-icon"></icon-svg> -->
            <span>{{ menu.name }}</span>
        </template>
        <sub-menu v-for="item in menu.list" :key="item.menuId" :menu="item" :dynamicMenuRoutes="dynamicMenuRoutes">
        </sub-menu>
    </el-submenu>
    <el-menu-item v-else :index="menu.menuId + ''" @click="gotoRouteHandle(menu)">
        <!-- <icon-svg :name="menu.icon || ''" class="site-sidebar__menu-icon"></icon-svg> -->
        <i class="site-sidebar__menu-icon fa" :class="menu.icon"></i>
        <span>{{ menu.name }}</span>
    </el-menu-item>
</template>

<script>
import SubMenu from './main-sidebar-sub-menu'
import {mapState} from "vuex";
export default {
    name: 'sub-menu',
    props: {
        menu: {
            type: Object,
            required: true
        },
        dynamicMenuRoutes: {
            type: Array,
            required: true
        }
    },
    components: {
        SubMenu
    },
    computed: mapState({
        sidebarLayoutSkin: state=>state.common.sidebarLayoutSkin,
        selectedAppid:state=>state.wxAccount.selectedAppid
    }),
    methods: {
        // 通过menuId与动态(菜单)路由进行匹配跳转至指定路由
        gotoRouteHandle(menu) {
            if (!this.selectedAppid && menu.url !=='wx/wx-account'){
                this.$message.warning("频道列表为空，请先到频道账号界面添加频道")
                return;
            }
            var route = this.dynamicMenuRoutes.filter(item => item.meta.menuId === menu.menuId)
            if (route.length >= 1) {
                this.$router.push({ name: route[0].name })
            }
        }
    }
}
</script>
