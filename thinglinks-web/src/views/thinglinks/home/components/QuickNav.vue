<template>
  <Row class="message-card-list" :gutter="16">
    <Col :span="8">
      <Card class="message-card-item">
        <img class="more" src="../../../../assets/images/index/ddd@3x.png" alt="" />
        <div class="user-info">
          <AvatarPreview
            :errorTxt="getUserInfo?.nickName?.substring(0, 1)"
            :fileId="getUserInfo?.avatarId"
            :isDef="true"
            :style="{ 'margin-right': '0.5rem' }"
            @click="openProfile"
          />
          <div class="user-name">
            <span class="name">{{ getUserInfo.nickName }}</span>
            <span class="address">{{ getUserInfo.workDescribe }}</span>
          </div>
        </div>
      </Card>
    </Col>
    <Col :span="8">
      <Card class="message-card-item">
        <div class="message-item-content">
          <p>公告</p>
          <List :split="false">
            <template v-for="item in proclamationList" :key="item.title">
              <ListItem>
                <ListItemMeta>
                  <template #description>
                    <span>
                      {{ item.date }}
                    </span>
                  </template>
                  <template #title>
                    <span class="title">
                      {{ item.title }}
                    </span>
                  </template>
                </ListItemMeta>
              </ListItem>
            </template>
          </List>
          <div class="show-more">
            <span>查看更多</span>
            <img src="../../../../assets/images/index/keyboard_backspace@2x.png" alt="" />
          </div>
        </div>
      </Card>
    </Col>
    <Col :span="8">
      <Card class="message-card-item">
        <div class="message-item-content">
          <p>5/23 待办</p>
          <List :split="false">
            <template v-for="item in toDoList" :key="item.title">
              <ListItem>
                <ListItemMeta>
                  <template #description>
                    <span>
                      {{ item.date }}
                    </span>
                  </template>
                  <template #title>
                    <span class="title">
                      {{ item.title }}
                    </span>
                  </template>
                  <template #avatar>
                    <img src="../../../../assets/images/index/Frame427319503@3x.png" alt="" />
                  </template>
                </ListItemMeta>
              </ListItem>
            </template>
          </List>
          <div class="show-more">
            <span>查看更多</span>
            <img src="../../../../assets/images/index/keyboard_backspace@2x.png" alt="" />
          </div>
        </div>
      </Card>
    </Col>
  </Row>
</template>
<script lang="ts" setup>
import { computed } from 'vue';
import { Card, Row, Col, List, ListItem, ListItemMeta } from 'ant-design-vue';
import { AvatarPreview } from '/@/components/AvatarPreview';
import { useRouter } from 'vue-router';
// import { navItems } from './data';
// import { Icon } from '/@/components/Icon';
import { useUserStore } from '/@/store/modules/user';
const { replace } = useRouter();
const userStore = useUserStore();
const getUserInfo = computed(() => {
  return userStore.getUserInfo;
});
const openProfile = () => {
  replace({
    name: 'profileIndex',
  });
};
// 待办列表
const toDoList = [
  {
    title: '待办1',
    date: '2021-09-01',
  },
  {
    title: '待办2',
    date: '2021-09-01',
  },
  {
    title: '待办3',
    date: '2021-09-01',
  },
];
// 公告列表
const proclamationList = [
  {
    title: '公告1',
    date: '2021-09-01',
  },
  {
    title: '公告2',
    date: '2021-09-01',
  },
  {
    title: '公告3',
    date: '2021-09-01',
  },
];
</script>
<style lang="less" scoped>
.message-card-list {
  display: flex;
  padding: 0 16px;
  box-sizing: border-box;

  :deep(.ant-col) {
    margin: 20px 0px;

    .message-card-item {
      border-radius: 20px;
      height: 400px;

      .ant-card-body {
        height: 400px;

        .user-info {
          height: 400px;
        }
      }
    }
  }

  .ant-col:nth-child(1) {
    .message-card-item {
      .ant-card-body {
        .more {
          position: absolute;
          right: 25px;
          top: 15px;
        }

        .user-info {
          width: 90%;
          display: flex;
          flex-direction: column;
          justify-content: space-around;
          align-items: center;
          padding: 20px 0;
          margin: auto;

          .ant-avatar {
            width: 130px;
            height: 130px;
          }

          .user-name {
            display: flex;
            flex-direction: column;

            span {
              text-align: center;
            }

            span:first-child {
              font-size: 28px;
              font-weight: bold;
            }
          }

          .user-data {
            width: 80%;
            margin: auto;
            display: flex;
            justify-content: space-around;
            align-items: center;

            .data-item {
              display: flex;
              flex-direction: column;

              span {
                display: inline-block;
                width: 80px;
                text-align: center;
              }

              span:last-child {
                font-size: 20px;
                font-weight: bold;
              }
            }
          }
        }
      }
    }
  }

  .ant-col:nth-child(2),
  .ant-col:nth-child(3) {
    .message-card-item {
      .message-item-content {
        p {
          font-size: 25px;
          font-weight: bold;
          color: #312f61;
        }

        .show-more {
          position: absolute;
          bottom: 25px;
          right: 60px;
          font-size: 18px;
          font-weight: bold;
          color: #401ced;
          display: flex;
          align-items: center;

          span {
            margin-right: 15px;
          }
        }
      }
    }
  }
}

.title {
  font-size: 18px;
  font-weight: bold;
  color: #312f61;
}
</style>
