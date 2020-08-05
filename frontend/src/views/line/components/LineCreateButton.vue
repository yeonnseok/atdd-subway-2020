<template>
  <Dialog :width="500" :close="close">
    <template slot="trigger">
      <v-btn class="mx-2 absolute right-30 z-1 line-create-button z-10" fab color="amber">
        <v-icon>mdi-plus</v-icon>
      </v-btn>
    </template>
    <template slot="title">
      <div class="width-100 text-center mt-6">노선 생성</div>
    </template>
    <template slot="text">
      <v-form ref="lineForm" v-model="valid" @submit.prevent>
        <v-text-field
          v-model="lineForm.name"
          :rules="rules.line.name"
          color="grey darken-1"
          label="노선 이름"
          placeholder="노선 이름"
          outlined
        ></v-text-field>
        <v-row>
          <v-col cols="6">
            <v-text-field
              v-model="lineForm.startTime"
              :rules="rules.line.startTime"
              color="grey darken-1"
              label="첫차 시간"
              placeholder="첫차 시간"
              outlined
            ></v-text-field>
          </v-col>
          <v-col cols="6">
            <v-text-field
              v-model="lineForm.endTime"
              :rules="rules.line.endTime"
              color="grey darken-1"
              label="막차 시간"
              placeholder="막차 시간"
              outlined
            ></v-text-field>
          </v-col>
          <v-col cols="6" class="pt-0">
            <v-text-field
              v-model="lineForm.intervalTime"
              :rules="rules.line.intervalTime"
              color="grey darken-1"
              label="간격"
              placeholder="간격"
              outlined
            ></v-text-field>
          </v-col>
          <v-col cols="6" class="pt-0">
            <v-text-field
              v-model="lineForm.extraFare"
              :rules="rules.line.extraFare"
              color="grey darken-1"
              label="추가 요금"
              placeholder="추가 요금"
              outlined
            ></v-text-field>
          </v-col>
        </v-row>
        <div>
          <v-text-field v-model="lineForm.color" :rules="rules.line.color" :value="lineForm.color" label="노선 색상" filled disabled></v-text-field>
          <p>
            노선의 색상을 아래 팔레트에서 선택해주세요.
          </p>
          <div class="d-flex">
            <div>
              <template v-for="(color, index) in lineColors">
                <v-btn :key="index" small class="color-button ma-1" depressed :color="color" @click="setLineColor(color)"></v-btn>
                <template v-if="index === 0"></template>
                <br v-if="index === 8 || index % 9 === 8" />
              </template>
            </div>
          </div>
        </div>
      </v-form>
    </template>
    <template slot="action">
      <v-btn :disabled="!valid" @click.prevent="onCreateLine" color="amber">확인</v-btn>
    </template>
  </Dialog>
</template>

<script>
import Dialog from '@/components/dialogs/Dialog'
import { LINE_COLORS, SNACKBAR_MESSAGES } from '@/utils/constants'
import dialog from '@/mixins/dialog'
import validator from '@/utils/validator'
import { mapActions, mapMutations } from 'vuex'
import { SHOW_SNACKBAR } from '@/store/shared/mutationTypes'
import { CREATE_LINE, DELETE_LINE, EDIT_LINE, FETCH_LINES } from '@/store/shared/actionTypes'

export default {
  name: 'LineCreateButton',
  mixins: [dialog],
  components: { Dialog },
  methods: {
    ...mapMutations([SHOW_SNACKBAR]),
    ...mapActions([FETCH_LINES, CREATE_LINE, DELETE_LINE, EDIT_LINE]),
    setLineColor(color) {
      this.lineForm.color = color
    },
    isValid() {
      return this.$refs.lineForm.validate()
    },
    async onCreateLine() {
      if (!this.isValid()) {
        return
      }
      try {
        await this.createLine(this.lineForm)
        await this.fetchLines()
        this.lineForm = {
          name: '',
          color: '',
          startTime: '',
          endTime: '',
          intervalTime: '',
          extraFare: ''
        }
        this.$refs.lineForm.resetValidation()
        this.closeDialog()
        this.showSnackbar(SNACKBAR_MESSAGES.COMMON.SUCCESS)
      } catch (e) {
        this.showSnackbar(SNACKBAR_MESSAGES.COMMON.FAIL)
      }
    }
  },
  data() {
    return {
      rules: { ...validator },
      isOption: true,
      lineForm: {
        name: '',
        color: '',
        startTime: '',
        endTime: '',
        intervalTime: '',
        extraFare: ''
      },
      valid: false,
      lineColors: [...LINE_COLORS]
    }
  }
}
</script>

<style lang="scss" scoped>
.color-button {
  min-width: 30px !important;
}

.line-create-button {
  bottom: -25px;
}
</style>
