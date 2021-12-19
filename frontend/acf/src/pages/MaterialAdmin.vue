<template>
  <div class="vatlieuadmin">
    <a-layout :style="{ background: 'white' }">
      <a-layout-content :style="{ margin: '30px 16px 0' }">
        <div
          :style="{
            minHeight: '360px',
            background: 'white',
          }"
        >
          <a-back-top :style="{ width: '5vh', height: '15vh' }" />
          <!-- menu trên -->
          <a-input
            placeholder="Mã vật liệu"
            :style="{ width: '150px', 'margin-right': '5px' }"
            v-model="dataSearch.codeMaterial"
            @pressEnter="submitSearch"
          />
          <a-input
            placeholder="Thông số"
            :style="{ width: '150px', 'margin-right': '5px' }"
            @pressEnter="submitSearch"
            v-model="dataSearch.frame"
          />
          <a-select
            placeholder="Nhóm vật liệu"
            mode="multiple"
            v-model="dataSearch.listGroupID"
            :filter-option="false"
            @search="fetchGroupMaterial"
            @change="submitSearch"
            :style="{ width: '150px', 'margin-right': '5px' }"
          >
            <a-select-option
              v-for="(group, index) in dataGroupMaterials"
              :value="group.id"
              :key="index"
            >
              {{ group.name }}
            </a-select-option>
          </a-select>
          <a-select
            placeholder="Đơn vị đo"
            mode="multiple"
            @change="submitSearch"
            v-model="dataSearch.listUnitId"
            :style="{ width: '150px', 'margin-right': '5px' }"
          >
            <a-select-option
              v-for="(unit, index) in dataUnits"
              :value="unit.id"
              :key="index"
            >
              {{ unit.name }}
            </a-select-option>
          </a-select>
          <a-select
            placeholder="Công ty"
            mode="multiple"
            @change="submitSearch"
            v-model="dataSearch.listIdCompany"
            style="width: 250px"
          >
            <a-select-option
              v-for="(company, index) in dataCompanys"
              :value="company.id"
              :key="index"
            >
              {{ company.name }}
            </a-select-option>
          </a-select>
          <a-button
            type="primary"
            @click="submitSearch"
            :style="{ 'margin-left': '5px' }"
          >
            <font-awesome-icon
              :icon="['fas', 'search']"
              :style="{ 'margin-right': '5px' }"
            />
            Tìm kiếm
          </a-button>
          <a-button
            type="primary"
            @click="showModalAdd"
            :style="{ 'margin-left': '5px' }"
          >
            <font-awesome-icon
              :icon="['fas', 'plus-square']"
              :style="{ 'margin-right': '5px' }"
            />
            Thêm vật liệu
          </a-button>
          <a-button
            type="primary"
            @click="showModalAddUnit"
            :style="{ 'margin-left': '5px' }"
          >
            <font-awesome-icon
              :icon="['fas', 'plus-square']"
              :style="{ 'margin-right': '5px' }"
            />
            Thêm đơn vị
          </a-button>
          <a-button
            type="primary"
            @click="showModalAddFrame"
            :style="{ 'margin-left': '5px' }"
          >
            <font-awesome-icon
              :icon="['fas', 'plus-square']"
              :style="{ 'margin-right': '5px' }"
            />
            Thêm khung và chiều cao
          </a-button>
          <!-- menu trên -->

          <!-- table content -->
          <div :style="{ 'padding-top': '10px' }">
            <a-table
              :columns="columns"
              :data-source="dataSourceTable"
              :pagination="pagination"
              :scroll="{ x: 1000 }"
              :rowKey="
                (record, index) => {
                  return index;
                }
              "
              @change="handleTableChange"
            >
              <template slot="image" slot-scope="text, record">
                <img
                  v-if="record.image !== null"
                  alt="example"
                  style="width: 20%"
                  :src="record.image"
                />
              </template>
              <template slot="name" slot-scope="text, record">
                {{ record.name }}
              </template>
              <template slot="parameter" slot-scope="text, record">
                {{ record.parameter }}
              </template>
              <template slot="nameGroup" slot-scope="text, record">
                {{ record.nameGroup }}
              </template>
              <template slot="unitId" slot-scope="text, record">
                {{ record.unitId }}
              </template>
              <template slot="listIdCompany" slot-scope="text, record">
                {{ record.listIdCompany }}
              </template>
              <template slot="action" slot-scope="text, record">
                <a-row>
                  <a-col :span="9">
                    <a-button
                      id="edit"
                      @click="
                        showModalEdit(
                          record.name,
                          record.idParameter,
                          record.parameter,
                          record.nameGroup,
                          record.unit,
                          record.company,
                          record.price,
                          record.image
                        )
                      "
                      :style="{ width: '44.25px' }"
                    >
                      <font-awesome-icon :icon="['fas', 'edit']" />
                    </a-button>
                  </a-col>
                  <a-col :span="9">
                    <a-popconfirm
                      v-if="dataSourceTable.length"
                      title="Bạn có chắc chắn muốn xóa không?"
                      ok-text="Đồng ý"
                      cancel-text="Hủy"
                      @confirm="deleteMaterial(record.id)"
                    >
                      <a-button id="delete">
                        <font-awesome-icon :icon="['fas', 'trash']" />
                      </a-button>
                    </a-popconfirm>
                  </a-col>
                </a-row>
              </template>
            </a-table>
          </div>
          <!-- table content -->

          <!-- popup add -->
          <a-modal v-model="visibleAdd" title="Thêm vật liệu">
            <template slot="footer">
              <a-button key="back" @click="handleCancel"> Hủy </a-button>
              <a-button
                key="submit"
                type="primary"
                :loading="loadingAdd"
                @click="checkFormAdd"
              >
                Lưu
              </a-button>
            </template>
            <a-row type="flex">
              <a-col flex="100px"
                ><span style="color: red">*</span>Mã vật liệu</a-col
              >
              <a-col flex="auto">
                <template v-for="tag in tags">
                  <a-tag
                    :key="tag"
                    :closable="true"
                    @close="() => handleClose(tag)"
                  >
                    {{ tag }}
                  </a-tag>
                </template>
                <a-input
                  v-if="inputVisible"
                  ref="input"
                  type="text"
                  size="small"
                  :style="{ width: '78px' }"
                  :value="inputValue"
                  @change="handleInputChange"
                  @blur="handleInputConfirm"
                  @keyup.enter="handleInputConfirm"
                />
                <a-tag
                  v-else
                  style="background: #fff; borderstyle: dashed"
                  @click="showInput"
                >
                  <font-awesome-icon :icon="['fas', 'plus']" /> Thêm vật liệu
                </a-tag>
                <div style="color: red" v-if="checkInputCodeMaterial.show">
                  {{ checkInputCodeMaterial.message }}
                </div>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px"><span style="color: red">*</span>Khung</a-col>
              <a-col flex="auto">
                <a-select
                  placeholder="Thông số"
                  mode="multiple"
                  v-model="dataAddMaterial.listIdFrame"
                  :filter-option="false"
                  @search="fetchFrame"
                  style="width: 100%"
                  @change="inputFrame"
                >
                  <a-select-option
                    v-for="(frameI, index) in dataFrameMaterials"
                    :value="frameI.id"
                    :key="index"
                  >
                    {{ frameI.frame }}
                  </a-select-option>
                </a-select>
                <div style="color: red" v-if="checkInputFrame.show">
                  {{ checkInputFrame.message }}
                </div>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px"
                ><span style="color: red">*</span>Chiều cao</a-col
              >
              <a-col flex="auto">
                <a-select
                  placeholder="Chiều cao"
                  mode="multiple"
                  v-model="dataAddMaterial.listIdHeight"
                  :filter-option="false"
                  @search="fetchHeight"
                  style="width: 100%"
                  @change="inputHeight"
                >
                  <a-select-option
                    v-for="(height, index) in dataHeights"
                    :value="height.id"
                    :key="index"
                  >
                    {{ height.frameHeight }}
                  </a-select-option>
                </a-select>
                <div style="color: red" v-if="checkInputHeight.show">
                  {{ checkInputHeight.message }}
                </div>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px"
                ><span style="color: red">*</span>Nhóm vật liệu</a-col
              >
              <a-col flex="auto">
                <a-select
                  placeholder="Nhóm vật liệu"
                  v-model="dataAddMaterial.idGroup"
                  style="width: 100%"
                  @change="inputGroupMaterial"
                >
                  <a-select-option
                    v-for="(group, index) in dataGroupMaterials"
                    :value="group.id"
                    :key="index"
                  >
                    {{ group.name }}
                  </a-select-option>
                </a-select>
                <div style="color: red" v-if="checkInputGroupMaterial.show">
                  {{ checkInputGroupMaterial.message }}
                </div>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px"
                ><span style="color: red">*</span>Đơn vị đo</a-col
              >
              <a-col flex="auto">
                <a-select
                  placeholder="Đơn vị đo"
                  v-model="dataAddMaterial.idUnit"
                  style="width: 100%"
                  @change="inputUnit"
                >
                  <a-select-option
                    v-for="(unit, index) in dataUnits"
                    :value="unit.id"
                    :key="index"
                  >
                    {{ unit.name }}
                  </a-select-option>
                </a-select>
                <div style="color: red" v-if="checkInputUnit.show">
                  {{ checkInputUnit.message }}
                </div>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px"
                ><span style="color: red">*</span>Công ty</a-col
              >
              <a-col flex="auto">
                <a-select
                  placeholder="Công ty"
                  v-model="dataAddMaterial.idCompany"
                  style="width: 100%"
                  @change="inputCompany"
                >
                  <a-select-option
                    v-for="(company, index) in dataCompanys"
                    :value="company.id"
                    :key="index"
                  >
                    {{ company.name }}
                  </a-select-option>
                </a-select>
                <div style="color: red" v-if="checkInputCompany.show">
                  {{ checkInputCompany.message }}
                </div>
              </a-col>
            </a-row>
            <br />

            <a-row type="flex">
              <a-col flex="100px"
                ><span style="color: red">*</span>Giá thành</a-col
              >
              <a-col flex="auto">
                <a-input-number
                  style="width: 100%"
                  :min="0"
                  v-model="dataAddMaterial.price"
                  @change="inputPrice"
                />
                <div style="color: red" v-if="checkInputPrice.show">
                  {{ checkInputPrice.message }}
                </div></a-col
              >
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px">Ảnh</a-col>
              <a-col flex="auto">
                <input
                  type="file"
                  accept=".jpg, .png"
                  ref="fileupload"
                  @change="importFile($event)"
                />
              </a-col>
            </a-row>
            <a-row type="flex" v-if="showImage">
              <a-col flex="100px">
                <img
                  alt="example"
                  style="width: 50%; margin-left: auto; margin-right: auto"
                  :src="url"
                />
              </a-col>
            </a-row>
          </a-modal>
          <!-- popup add -->

          <!-- popup edit-->
          <a-modal v-model="visibleEdit" title="Chỉnh sửa vật liệu">
            <template slot="footer">
              <a-button key="back" @click="handleCancel"> Hủy </a-button>
              <a-button
                key="submit"
                type="primary"
                :loading="loadingEdit"
                @click="checkFormEdit"
              >
                Lưu
              </a-button>
            </template>
            <a-row type="flex">
              <a-col flex="100px"
                ><span style="color: red">*</span>Mã vật liệu</a-col
              >
              <a-col flex="auto">
                <a-input
                  v-model="dataEditMaterial.name"
                  style="width: 100%"
                  disabled
                />
                <div style="color: red" v-if="checkInputCodeMaterial.show">
                  {{ checkInputCodeMaterial.message }}
                </div>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px"
                ><span style="color: red">*</span>Thông số</a-col
              >
              <a-col flex="auto">
                <a-input
                  v-model="dataEditMaterial.parameter"
                  style="width: 100%"
                  disabled
                />
                <div style="color: red" v-if="checkInputFrame.show">
                  {{ checkInputFrame.message }}
                </div>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px"
                ><span style="color: red">*</span>Nhóm vật liệu</a-col
              >
              <a-col flex="auto">
                <a-input
                  v-model="dataEditMaterial.nameGroup"
                  style="width: 100%"
                  disabled
                />
                <div style="color: red" v-if="checkInputGroupMaterial.show">
                  {{ checkInputGroupMaterial.message }}
                </div>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px"
                ><span style="color: red">*</span>Đơn vị đo</a-col
              >
              <a-col flex="auto">
                <a-input
                  v-model="dataEditMaterial.unit"
                  style="width: 100%"
                  disabled
                />
                <div style="color: red" v-if="checkInputUnit.show">
                  {{ checkInputUnit.message }}
                </div>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px"
                ><span style="color: red">*</span>Công ty</a-col
              >
              <a-col flex="auto">
                <a-input
                  v-model="dataEditMaterial.company"
                  style="width: 100%"
                  disabled
                />
                <div style="color: red" v-if="checkInputCompany.show">
                  {{ checkInputCompany.message }}
                </div>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px"
                ><span style="color: red">*</span>Giá thành</a-col
              >
              <a-col flex="auto">
                <a-input-number
                  style="width: 100%"
                  :min="0"
                  v-model="dataEdit.price"
                  @change="inputPriceEdit"
                />
                <div style="color: red" v-if="checkInputPrice.show">
                  {{ checkInputPrice.message }}
                </div>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px">Ảnh</a-col>
              <a-col flex="auto">
                <input
                  type="file"
                  accept=".jpg, .png"
                  ref="fileupload"
                  @change="importFileEdit($event)"
                />
              </a-col>
            </a-row>
            <a-row type="flex" v-if="showImage">
              <a-col flex="100px">
                <img
                  alt="example"
                  style="width: 50%; margin-left: auto; margin-right: auto"
                  :src="url"
                />
              </a-col>
            </a-row>
          </a-modal>
          <!-- popup edit-->

          <!-- popup add unit-->
          <a-modal v-model="visibleAddUnit" title="Thêm đơn vị">
            <template slot="footer">
              <a-button key="back" @click="handleCancel"> Hủy </a-button>
              <a-button
                key="submit"
                type="primary"
                @click="checkFormAddUnit"
                :loading="loadingAddUnit"
              >
                Lưu
              </a-button>
            </template>
            <a-row type="flex">
              <a-col flex="100px"
                ><span style="color: red">*</span>Mã vật liệu :</a-col
              >
              <a-col flex="auto">
                <a-select
                  v-model="dataAddUnitMaterial.idMaterial"
                  style="width: 100%"
                  @change="handleDonViByMVL"
                >
                  <a-select-option
                    v-for="(frame, index) in dataMaterials"
                    :value="frame.id"
                    :key="index"
                  >
                    {{ frame.name }}
                  </a-select-option>
                </a-select>
                <div style="color: red" v-if="checkInputCodeMaterial.show">
                  {{ checkInputCodeMaterial.message }}
                </div>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px"
                ><span style="color: red">*</span>Đơn vị đo :</a-col
              >
              <a-col flex="auto">
                <a-select
                  v-model="dataAddUnitMaterial.idUnit"
                  style="width: 100%"
                  @change="handleMVLByDonVi"
                >
                  <a-select-option
                    v-for="(unit, index) in dataUnits"
                    :value="unit.id"
                    :key="index"
                  >
                    {{ unit.name }}
                  </a-select-option>
                </a-select>
                <div style="color: red" v-if="checkInputUnit.show">
                  {{ checkInputUnit.message }}
                </div>
              </a-col>
            </a-row>
          </a-modal>
          <!-- popup add unit-->

          <!-- popup add frame-->
          <a-modal v-model="visibleAddFrame" title="Thêm khung và chiều cao">
            <template slot="footer">
              <a-button key="back" @click="handleCancel"> Hủy </a-button>
              <a-button key="reset" type="danger" @click="resetFrame">
                Reset
              </a-button>
              <a-button
                key="submit"
                type="primary"
                @click="checkFormAddFrame"
                :loading="loadingAddFrameHeight"
              >
                Lưu
              </a-button>
            </template>
            <a-row type="flex">
              <a-col flex="100px"
                ><span style="color: red">*</span>Mã vật liệu :</a-col
              >
              <a-col flex="auto">
                <a-select
                  placeholder="Mã vật liệu"
                  v-model="dataAddFrameHeightMaterial.idMaterial"
                  style="width: 100%"
                  @change="handleMVLByKhungvaChieuCao"
                  :disabled="disable"
                >
                  <a-select-option
                    v-for="(frame, index) in dataMaterials"
                    :value="frame.id"
                    :key="index"
                  >
                    {{ frame.name }}
                  </a-select-option>
                </a-select>
                <div style="color: red" v-if="checkInputCodeMaterial.show">
                  {{ checkInputCodeMaterial.message }}
                </div>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px">
                <span style="color: red">*</span>Khung :</a-col
              >
              <a-col flex="auto">
                <a-select
                  placeholder="Thông số"
                  v-model="dataAddFrameHeightMaterial.idFrame"
                  style="width: 100%"
                  @change="handleKhungByMVLvaChieuCao"
                  :disabled="disable"
                >
                  <a-select-option
                    v-for="(frameI, index) in dataFrameMaterials"
                    :value="frameI.id"
                    :key="index"
                  >
                    {{ frameI.frame }}
                  </a-select-option>
                </a-select>
                <div style="color: red" v-if="checkInputFrame.show">
                  {{ checkInputFrame.message }}
                </div>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px">
                <span style="color: red">*</span>Chiều cao :</a-col
              >
              <a-col flex="auto">
                <a-select
                  placeholder="Chiều cao"
                  v-model="dataAddFrameHeightMaterial.idHeight"
                  style="width: 100%"
                  @change="handleChieuCaoByMVLvaKhung"
                  :disabled="disable"
                >
                  <a-select-option
                    v-for="(height, index) in dataHeights"
                    :value="height.id"
                    :key="index"
                  >
                    {{ height.frameHeight }}
                  </a-select-option>
                </a-select>
                <div style="color: red" v-if="checkInputHeight.show">
                  {{ checkInputHeight.message }}
                </div>
              </a-col>
            </a-row>
          </a-modal>
          <!-- popup add frame-->
        </div>
      </a-layout-content>
    </a-layout>
  </div>
</template>
 <script>
import companyService from "@/service/companyService.js";
import unitService from "@/service/unitService.js";
import groupMaterialService from "@/service/groupMaterialService.js";
import heightService from "@/service/heightService.js";
import vatLieuAdminService from "@/service/materialAdminService.js";
import fileService from "../service/fileService";

export default {
  name: "MaterialAdmin",
  components: {},
  data() {
    return {
      url: "",
      showImage: false,
      pagination: {
        current: 1,
        pageSize: 10,
        total: 0,
      },
      dataSearch: {
        codeMaterial: "",
        frame: "",
        listGroupID: [],
        listIdCompany: [],
        listUnitId: [],
        pageIndex: 1,
        pageSize: 10,
        total: 0,
      },
      dataSourceTable: [],
      dataFrames: [],
      dataFrame: {
        name: "",
        pageIndex: 1,
        pageSize: 10,
      },
      dataHeights: [],
      dataHeight: {
        frameHeight: "",
        pageIndex: 1,
        pageSize: 10,
      },
      dataHeightMaterial: [],
      dataGroupMaterials: "",
      dataGroupMaterial: {
        name: "",
        pageIndex: 1,
        pageSize: 10,
      },
      dataUnits: "",
      dataUnit: {
        name: "",
        pageIndex: 1,
        pageSize: 10,
      },
      dataUnitMaterial: [],
      dataAllFramseMaterial: [],
      dataCompanys: "",
      dataCompany: {
        address: "",
        name: "",
        phone: "",
        pageIndex: 1,
        pageSize: 10,
      },
      dataFrameMaterials: [],
      dataFrameMaterial: {
        frame: "",
        pageIndex: 1,
        pageSize: 10,
      },
      dataAddMaterial: {
        idCompany: "",
        idGroup: "",
        idUnit: "",
        listIdFrame: [],
        listIdHeight: [],
        listName: [],
        price: "",
        image: "",
      },
      dataAddFrameHeightMaterial: {
        idFrame: "",
        idHeight: "",
        idMaterial: "",
        idUnit: "",
      },
      dataAddUnitMaterial: {
        idFrame: "",
        idHeight: "",
        idMaterial: "",
        idUnit: "",
        price: "",
      },
      dataEdit: {
        idParameter: "",
        price: "",
        image: "",
      },
      dataEditMaterial: {
        parameter: "",
        name: "",
        nameGroup: "",
        company: "",
        unit: "",
      },
      dataForm: {
        id1: 0,
        id2: 0,
        name1: "",
        name2: "",
      },
      dataHeightsByMaterialAndFrames: [],
      dataSelect: [],
      dataMaterials: [],
      listFrameHeightMaterial: [],
      columns: [
        {
          title: "STT",
          width: 100,
          dataIndex: "id",
          key: "id",
          fixed: "left",
        },
        {
          title: "Ảnh",
          dataIndex: "image",
          key: "image",
          width: 150,
          scopedSlots: { customRender: "image" },
        },
        {
          title: "Mã vật liệu",
          dataIndex: "name",
          key: "name",
          width: 150,
          scopedSlots: { customRender: "name" },
        },
        {
          title: "Thông số",
          dataIndex: "parameter",
          key: "parameter",
          width: 150,
          scopedSlots: { customRender: "parameter" },
        },
        {
          title: "Nhóm vật liệu",
          dataIndex: "nameGroup",
          key: "nameGroup",
          width: 150,
          scopedSlots: { customRender: "nameGroup" },
        },
        {
          title: "Đơn vị đo",
          dataIndex: "unit",
          key: "unit",
          width: 150,
          scopedSlots: { customRender: "unit" },
        },
        {
          title: "Công ty",
          dataIndex: "company",
          key: "company",
          width: 150,
          scopedSlots: { customRender: "company" },
        },
        {
          title: "Giá",
          dataIndex: "price",
          key: "price",
          width: 150,
          scopedSlots: { customRender: "price" },
        },
        {
          title: "",
          dataIndex: "action",
          key: "action",
          fixed: "right",
          width: 180,
          scopedSlots: { customRender: "action" },
        },
      ],
      visibleAdd: false,
      visibleEdit: false,
      visibleAddUnit: false,
      visibleAddFrame: false,
      tags: [],
      inputVisible: false,
      inputValue: "",
      disable: false,
      loadingAdd: false,
      loadingEdit: false,
      checkInputFrame: {
        show: false,
        message: "",
      },
      checkInputHeight: {
        show: false,
        message: "",
      },
      checkInputGroupMaterial: {
        show: false,
        message: "",
      },
      checkInputUnit: {
        show: false,
        message: "",
      },
      checkInputCodeMaterial: {
        show: false,
        message: "",
      },
      checkInputCompany: {
        show: false,
        message: "",
      },
      checkInputPrice: {
        show: false,
        message: "",
      },
      loadingAddUnit: false,
      loadingAddFrameHeight: false,
    };
  },
  computed: {},
  created() {
    this.submitSearch();
    this.getAllHeight();
    this.getAllGroupMaterial();
    this.getAllUnit();
    this.getAllCompany();
    this.getAllFrame();
    this.getMaterials();
  },
  methods: {
    importFileEdit(event1) {
      if (event1.target.files[0]) {
        this.dataEdit.image = event1.target.files[0];
        this.url = window.URL.createObjectURL(event1.target.files[0]);
        this.showImage = true;
      }
    },
    importFile(event1) {
      if (event1.target.files[0]) {
        this.dataAddMaterial.image = event1.target.files[0];
        this.url = window.URL.createObjectURL(event1.target.files[0]);
        this.showImage = true;
      }
    },
    handleTableChange(pagination) {
      this.dataSearch.pageIndex = pagination.current;
      this.pagination = pagination;
      vatLieuAdminService
        .searchMaterial(this.dataSearch)
        .then((response) => {
          this.dataSourceTable = response.data.data;
          for (let i = 0; i < this.dataSourceTable.length; i++) {
            if (this.dataSourceTable[i].image !== null) {
              vatLieuAdminService
                .preview(this.dataSourceTable[i].image)
                .then((response) => {
                  this.dataSourceTable[i].image = window.URL.createObjectURL(
                    response.data
                  );
                })
                .catch((e) => {
                  console.log(e);
                });
            }
          }
          this.dataSearch.total = response.data.total;
          this.pagination.total = response.data.total;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    submitSearch() {

      this.dataSearch.total = 0;
      this.dataSearch.pageIndex = 1;
      vatLieuAdminService
        .searchMaterial(this.dataSearch)
        .then((response) => {
          this.dataSourceTable = response.data.data;
          for (let i = 0; i < this.dataSourceTable.length; i++) {
            if (this.dataSourceTable[i].image !== null) {
              vatLieuAdminService
                .preview(this.dataSourceTable[i].image)
                .then((response) => {
                  this.dataSourceTable[i].image = window.URL.createObjectURL(
                    response.data
                  );
                })
                .catch((e) => {
                  console.log(e);
                });
            }
          }
          this.dataSearch.total = response.data.total;
          this.pagination.total = response.data.total;
          this.pagination.current = 1;
        })
        .catch((e) => {
          console.log(e);
        });
    },

    //reset
    resetFrame() {
      this.dataSelect = [];
      this.disable = false;
      this.getMaterials();
      this.getAllFrame();
      this.getAllHeight();
      this.dataAddFrameHeightMaterial.idMaterial = "";
      this.dataAddFrameHeightMaterial.idFrame = "";
      this.dataAddFrameHeightMaterial.idHeight = "";

      this.checkInputCodeMaterial.show = false;
      this.checkInputCodeMaterial.message = "";

      this.checkInputFrame.show = false;
      this.checkInputFrame.message = "";

      this.checkInputHeight.show = false;
      this.checkInputHeight.message = "";

      this.checkInputGroupMaterial.show = false;
      this.checkInputGroupMaterial.message = "";

      this.checkInputUnit.show = false;
      this.checkInputUnit.message = "";

      this.checkInputCompany.show = false;
      this.checkInputCompany.message = "";

      this.checkInputPrice.show = false;
      this.checkInputPrice.message = "";
    },

    //get vật liệu
    getMaterials() {
      vatLieuAdminService
        .getMaterials()
        .then((response) => {
          this.dataMaterials = response.data.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },

    // add vật liệu
    showModalAdd() {
      this.tags = [];
      this.dataAddMaterial.idCompany = "";
      this.dataAddMaterial.idGroup = "";
      this.dataAddMaterial.idUnit = "";
      this.dataAddMaterial.listIdFrame = [];
      this.dataAddMaterial.listIdHeight = [];
      this.dataAddMaterial.listName = [];
      this.dataAddMaterial.price = "";
      this.dataAddMaterial.image = "";
      if (this.$refs.fileupload != null) {
        this.$refs.fileupload.value = null;
      }
      this.url = "";

      this.checkInputCodeMaterial.show = false;
      this.checkInputCodeMaterial.message = "";

      this.checkInputFrame.show = false;
      this.checkInputFrame.message = "";

      this.checkInputHeight.show = false;
      this.checkInputHeight.message = "";

      this.checkInputGroupMaterial.show = false;
      this.checkInputGroupMaterial.message = "";

      this.checkInputUnit.show = false;
      this.checkInputUnit.message = "";

      this.checkInputCompany.show = false;
      this.checkInputCompany.message = "";

      this.checkInputPrice.show = false;
      this.checkInputPrice.message = "";

      this.showImage = false;
      this.visibleAdd = true;
    },
    checkFormAdd() {
      let check = true;
      if (this.tags != null && this.tags.length != 0) {
        this.checkInputCodeMaterial.show = false;
        this.checkInputCodeMaterial.message = "";
      } else {
        check = false;
        this.checkInputCodeMaterial.show = true;
        this.checkInputCodeMaterial.message = "Bạn phải điền mã vật liệu";
      }
      if (
        this.dataAddMaterial.listIdFrame != null &&
        this.dataAddMaterial.listIdFrame.length != 0
      ) {
        this.checkInputFrame.show = false;
        this.checkInputFrame.message = "";
      } else {
        check = false;
        this.checkInputFrame.show = true;
        this.checkInputFrame.message = "Bạn phải điền khung vật liệu";
      }
      if (
        this.dataAddMaterial.listIdHeight != null &&
        this.dataAddMaterial.listIdHeight.length != 0
      ) {
        this.checkInputHeight.show = false;
        this.checkInputHeight.message = "";
      } else {
        check = false;
        this.checkInputHeight.show = true;
        this.checkInputHeight.message = "Bạn phải điền chiều cao";
      }
      if (
        this.dataAddMaterial.idGroup != null &&
        this.dataAddMaterial.idGroup != ""
      ) {
        this.checkInputGroupMaterial.show = false;
        this.checkInputGroupMaterial.message = "";
      } else {
        check = false;
        this.checkInputGroupMaterial.show = true;
        this.checkInputGroupMaterial.message = "Bạn phải điền nhóm vật liệu";
      }
      if (
        this.dataAddMaterial.idUnit != null &&
        this.dataAddMaterial.idUnit != ""
      ) {
        this.checkInputUnit.show = false;
        this.checkInputUnit.message = "";
      } else {
        check = false;
        this.checkInputUnit.show = true;
        this.checkInputUnit.message = "Bạn phải điền đơn vị";
      }
      if (
        this.dataAddMaterial.idCompany != null &&
        this.dataAddMaterial.idCompany != ""
      ) {
        this.checkInputCompany.show = false;
        this.checkInputCompany.message = "";
      } else {
        check = false;
        this.checkInputCompany.show = true;
        this.checkInputCompany.message = "Bạn phải điền công ty";
      }
      if (
        this.dataAddMaterial.price != null &&
        this.dataAddMaterial.price != ""
      ) {
        this.checkInputPrice.show = false;
        this.checkInputPrice.message = "";
      } else {
        check = false;
        this.checkInputPrice.show = true;
        this.checkInputPrice.message = "Bạn phải điền giá thành";
      }
      if (check) {
        this.submitAdd();
      }
    },
    inputFrame() {
      if (
        this.dataAddMaterial.listIdFrame != null &&
        this.dataAddMaterial.listIdFrame.length != 0
      ) {
        this.checkInputFrame.show = false;
        this.checkInputFrame.message = "";
      } else {
        this.checkInputFrame.show = true;
        this.checkInputFrame.message = "Bạn phải điền khung vật liệu";
      }
    },
    inputHeight() {
      if (
        this.dataAddMaterial.listIdHeight != null &&
        this.dataAddMaterial.listIdHeight.length != 0
      ) {
        this.checkInputHeight.show = false;
        this.checkInputHeight.message = "";
      } else {
        this.checkInputHeight.show = true;
        this.checkInputHeight.message = "Bạn phải điền chiều cao";
      }
    },
    inputGroupMaterial() {
      if (
        this.dataAddMaterial.idGroup != null &&
        this.dataAddMaterial.idGroup != ""
      ) {
        this.checkInputGroupMaterial.show = false;
        this.checkInputGroupMaterial.message = "";
      } else {
        this.checkInputGroupMaterial.show = true;
        this.checkInputGroupMaterial.message = "Bạn phải điền nhóm vật liệu";
      }
    },
    inputUnit() {
      if (
        this.dataAddMaterial.idUnit != null &&
        this.dataAddMaterial.idUnit != ""
      ) {
        this.checkInputUnit.show = false;
        this.checkInputUnit.message = "";
      } else {
        this.checkInputUnit.show = true;
        this.checkInputUnit.message = "Bạn phải điền đơn vị";
      }
    },
    inputCompany() {
      if (
        this.dataAddMaterial.idCompany != null &&
        this.dataAddMaterial.idCompany != ""
      ) {
        this.checkInputCompany.show = false;
        this.checkInputCompany.message = "";
      } else {
        this.checkInputCompany.show = true;
        this.checkInputCompany.message = "Bạn phải điền công ty";
      }
    },
    inputPrice() {
      if (
        this.dataAddMaterial.price != null &&
        this.dataAddMaterial.price != ""
      ) {
        this.checkInputPrice.show = false;
        this.checkInputPrice.message = "";
      } else {
        this.checkInputPrice.show = true;
        this.checkInputPrice.message = "Bạn phải điền giá thành";
      }
    },
    //submit add
    submitAdd() {
      this.loadingAdd = true;
      if (this.dataAddMaterial.image != "") {
        fileService
          .uploadImage(this.dataAddMaterial.image)
          .then((response) => {
            this.dataAddMaterial.listName = this.tags;
            this.dataAddMaterial.image = response.data.data;
            vatLieuAdminService
              .addMaterial(this.dataAddMaterial)
              .then((response) => {
                this.submitSearch();
                this.loadingAdd = false;
                if (response.data.data) {
                  let type = "success";
                  let message = "Thêm vật liệu mới";
                  let description = response.data.message;
                  this.notifi(type, message, description);
                } else {
                  let type = "error";
                  let message = "Thêm vật liệu mới";
                  let description = response.data.message;
                  this.notifi(type, message, description);
                  vatLieuAdminService.deleteImage(this.dataAdd.image);
                }
                this.visibleAdd = false;
                this.loadingAdd = false;
              })
              .catch(() => {
                vatLieuAdminService.deleteImage(this.dataAdd.image);
                this.loadingAdd = false;
                this.visibleAdd = false;
              });
          })
          .catch((e) => {
            console.log(e);
            this.loadingAdd = false;
            this.visibleAdd = false;
          });
      } else {
        this.dataAddMaterial.listName = this.tags;
        vatLieuAdminService
          .addMaterial(this.dataAddMaterial)
          .then((response) => {
            this.submitSearch();
            this.loadingAdd = false;
            if (response.data.data) {
              let type = "success";
              let message = "Thêm vật liệu mới";
              let description = response.data.message;
              this.notifi(type, message, description);
            } else {
              let type = "error";
              let message = "Thêm vật liệu mới";
              let description = response.data.message;
              this.notifi(type, message, description);
            }
            this.visibleAdd = false;
            this.loadingAdd = false;
          })
          .catch(() => {
            this.loadingAdd = false;
            this.visibleAdd = false;
          });
      }
    },

    //delete Material
    deleteMaterial(id) {
      vatLieuAdminService
        .deleteMaterial(id)
        .then((response) => {
          if (response.data.data) {
            let type = "success";
            let message = "Xóa";
            let description = "Xóa vật liệu thành công";
            this.notifi(type, message, description);
            this.submitSearch();
          } else {
            let type = "error";
            let message = "Xóa";
            let description = "Xóa vật liệu không thành công";
            this.notifi(type, message, description);
            this.submitSearch();
          }
        })
        .catch((e) => {
          console.log(e);
        });
    },

    //show model Update
    showModalEdit(
      name,
      idParameter,
      parameter,
      nameGroup,
      unit,
      company,
      price,
      image
    ) {
      this.dataEditMaterial.name = name;
      this.dataEdit.idParameter = idParameter;
      this.dataEditMaterial.parameter = parameter;
      this.dataEditMaterial.nameGroup = nameGroup;
      this.dataEditMaterial.unit = unit;
      this.dataEditMaterial.company = company;
      this.dataEdit.price = price;
      this.url = image;
      this.dataEdit.image = "";
      this.checkInputPrice.show = false;
      this.checkInputPrice.message = "";
      if (this.url != null) {
        this.showImage = true;
      } else {
        this.showImage = false;
      }
      if (this.$refs.fileupload != null) {
        this.$refs.fileupload.value = null;
      }
      this.visibleEdit = true;
    },
    checkFormEdit() {
      let check = true;
      if (
        this.dataEditMaterial.name != null &&
        this.dataEditMaterial.name != ""
      ) {
        this.checkInputCodeMaterial.show = false;
        this.checkInputCodeMaterial.message = "";
      } else {
        check = false;
        this.checkInputCodeMaterial.show = true;
        this.checkInputCodeMaterial.message = "Bạn phải điền khung vật liệu";
      }
      if (
        this.dataEditMaterial.parameter != null &&
        this.dataEditMaterial.parameter != ""
      ) {
        this.checkInputFrame.show = false;
        this.checkInputFrame.message = "";
      } else {
        check = false;
        this.checkInputFrame.show = true;
        this.checkInputFrame.message = "Bạn phải điền khung vật liệu";
      }
      if (
        this.dataEditMaterial.nameGroup != null &&
        this.dataEditMaterial.nameGroup != ""
      ) {
        this.checkInputGroupMaterial.show = false;
        this.checkInputGroupMaterial.message = "";
      } else {
        check = false;
        this.checkInputGroupMaterial.show = true;
        this.checkInputGroupMaterial.message = "Bạn phải điền nhóm vật liệu";
      }
      if (
        this.dataEditMaterial.unit != null &&
        this.dataEditMaterial.unit != ""
      ) {
        this.checkInputUnit.show = false;
        this.checkInputUnit.message = "";
      } else {
        check = false;
        this.checkInputUnit.show = true;
        this.checkInputUnit.message = "Bạn phải điền đơn vị";
      }
      if (
        this.dataEditMaterial.company != null &&
        this.dataEditMaterial.company != ""
      ) {
        this.checkInputCompany.show = false;
        this.checkInputCompany.message = "";
      } else {
        check = false;
        this.checkInputCompany.show = true;
        this.checkInputCompany.message = "Bạn phải điền công ty";
      }
      if (this.dataEdit.price != null && this.dataEdit.price != "") {
        this.checkInputPrice.show = false;
        this.checkInputPrice.message = "";
      } else {
        check = false;
        this.checkInputPrice.show = true;
        this.checkInputPrice.message = "Bạn phải điền giá thành";
      }
      if (check) {
        this.submitUpdate();
      }
    },
    inputPriceEdit() {
      if (this.dataEdit.price != null && this.dataEdit.price != "") {
        this.checkInputPrice.show = false;
        this.checkInputPrice.message = "";
      } else {
        this.checkInputPrice.show = true;
        this.checkInputPrice.message = "Bạn phải điền giá thành";
      }
    },
    //submit update
    submitUpdate() {
      this.loadingEdit = true;
      if (this.dataEdit.image != "") {
        fileService
          .uploadImage(this.dataEdit.image)
          .then((response) => {
            this.dataEdit.image = response.data.data;
            vatLieuAdminService
              .updateMaterial(this.dataEdit)
              .then((response) => {
                this.submitSearch();
                if (response.data.data) {
                  let type = "success";
                  let message = "Cập nhật";
                  let description = "Cập nhật đơn thành công";
                  this.notifi(type, message, description);
                } else {
                  let type = "error";
                  let message = "Cập nhật";
                  let description = "Cập nhật đơn không thành công";
                  this.notifi(type, message, description);
                  vatLieuAdminService.deleteImage(this.dataEdit.image);
                }
                this.loadingEdit = false;
                this.visibleEdit = false;
              })
              .catch(() => {
                vatLieuAdminService.deleteImage(this.dataEdit.image);
                this.loadingAdd = false;
                this.visibleEdit = false;
              });
          })
          .catch((e) => {
            console.log(e);
            this.loadingEdit = false;
            this.visibleEdit = false;
          });
      } else {
        vatLieuAdminService
          .updateMaterial(this.dataEdit)
          .then((response) => {
            this.submitSearch();
            if (response.data.data) {
              let type = "success";
              let message = "Cập nhật";
              let description = "Cập nhật đơn thành công";
              this.notifi(type, message, description);
            } else {
              let type = "error";
              let message = "Cập nhật";
              let description = "Cập nhật đơn không thành công";
              this.notifi(type, message, description);
            }
            this.loadingEdit = false;
            this.visibleEdit = false;
          })
          .catch(() => {
            this.loadingEdit = false;
            this.visibleEdit = false;
          });
      }
    },

    //add đơn vị
    showModalAddUnit() {
      this.visibleAddUnit = true;
      this.dataAddUnitMaterial.idMaterial = "";
      this.dataAddUnitMaterial.idUnit = "";
      this.getAllUnit();
      this.getMaterials();
      this.checkInputCodeMaterial.show = false;
      this.checkInputCodeMaterial.message = "";
      this.checkInputUnit.show = false;
      this.checkInputUnit.message = "";
    },
    checkFormAddUnit() {
      let check = true;
      if (
        this.dataAddUnitMaterial.idMaterial != null &&
        this.dataAddUnitMaterial.idMaterial != ""
      ) {
        this.checkInputCodeMaterial.show = false;
        this.checkInputCodeMaterial.message = "";
      } else {
        check = false;
        this.checkInputCodeMaterial.show = true;
        this.checkInputCodeMaterial.message = "Bạn phải điền mã vật liệu";
      }
      if (
        this.dataAddUnitMaterial.idUnit != null &&
        this.dataAddUnitMaterial.idUnit != ""
      ) {
        this.checkInputUnit.show = false;
        this.checkInputUnit.message = "";
      } else {
        check = false;
        this.checkInputUnit.show = true;
        this.checkInputUnit.message = "Bạn phải điền đơn vị đo";
      }
      if (check) {
        this.submitAddUnit();
      }
    },
    //thay đổi đơn vị theo MVL
    getUnitByMaterial(id) {
      this.dataAddUnitMaterial.idUnit = "";
      vatLieuAdminService
        .getUnitByMaterial(id)
        .then((response) => {
          if (response.data.data.length != 0) {
            this.dataUnits = response.data.data;
            let type = "success";
            let message = "Thành công";
            let description = "Lấy đơn vị theo mã vật liệu thành công";
            this.notifi(type, message, description);
          } else {
            let type = "error";
            let message = "Không thành công";
            let description = "Lấy đơn vị theo mã vật liệu không thành công";
            this.notifi(type, message, description);
          }
        })
        .catch((e) => {
          console.log(e);
        });
    },

    //thay đổi đơn vị theo MVL
    handleDonViByMVL(value) {
      if (
        this.dataAddUnitMaterial.idMaterial != null &&
        this.dataAddUnitMaterial.idMaterial != ""
      ) {
        this.checkInputCodeMaterial.show = false;
        this.checkInputCodeMaterial.message = "";
      } else {
        this.checkInputCodeMaterial.show = true;
        this.checkInputCodeMaterial.message = "Bạn phải điền đơn vị đo";
      }
      this.getUnitByMaterial(value);
    },

    //thay đổi MVL theo đơn vị
    handleMVLByDonVi() {
      if (
        this.dataAddUnitMaterial.idUnit != null &&
        this.dataAddUnitMaterial.idUnit != ""
      ) {
        this.checkInputUnit.show = false;
        this.checkInputUnit.message = "";
      } else {
        this.checkInputUnit.show = true;
        this.checkInputUnit.message = "Bạn phải điền mã vật liệu";
      }
    },
    //thay đổi MVL theo đơn vị
    searchMaterialByUnit(unit) {
      vatLieuAdminService
        .searchMaterialByUnit(unit)
        .then((response) => {
          if (response.data.data) {
            this.dataMaterials = response.data.data;
            let type = "success";
            let message = "Bạn đã chọn đơn vị";
            let description = response.data.message;
            this.notifi(type, message, description);
          } else {
            let type = "error";
            let message = "Bạn đã chọn đơn vị";
            let description = response.data.message;
            this.notifi(type, message, description);
          }
        })
        .catch((e) => {
          console.log(e);
        });
    },

    //submit add unit
    submitAddUnit() {
      this.loadingAddUnit = true;
      vatLieuAdminService
        .addUnitMaterial(this.dataAddUnitMaterial)
        .then((response) => {
          this.submitSearch();
          if (response.data.data) {
            let type = "success";
            let message = "Thêm đơn vị mới";
            let description = response.data.message;
            this.notifi(type, message, description);
          } else {
            let type = "error";
            let message = "Thêm đơn vị mới";
            let description = response.data.message;
            this.notifi(type, message, description);
          }
          this.loadingAddUnit = false;
          this.visibleAddUnit = false;
        })
        .catch(() => {
          this.loadingAddUnit = false;
          this.visibleAddUnit = false;
        });
    },

    //thay đổi chiều cao theo MVL và khung
    getHeightsByMaterialAndFrame() {
      vatLieuAdminService
        .getHeightsByMaterialAndFrame(this.dataForm)
        .then((response) => {
          if (response.data.data.length != 0) {
            let type = "success";
            let message = "Thành công";
            let description =
              "Lấy chiều cao theo " +
              this.dataForm.name1 +
              " và " +
              this.dataForm.name2 +
              " thành công";
            this.notifi(type, message, description);
          } else {
            let type = "error";
            let message = "Không tồn tại";
            let description =
              "Tất cả chiều cao theo " +
              this.dataForm.name1 +
              " và " +
              this.dataForm.name2 +
              " đều đã tồn tại";
            this.notifi(type, message, description);
          }
          this.dataHeights = response.data.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },

    //thay đổi khung theo MVL và chiều cao
    getFrameByMaterialAndHeight() {
      vatLieuAdminService
        .getFrameByMaterialAndHeight(this.dataForm)
        .then((response) => {
          if (response.data.data.length != 0) {
            let type = "success";
            let message = "Thành công";
            let description =
              "Lấy khung theo " +
              this.dataForm.name1 +
              " và " +
              this.dataForm.name2 +
              " thành công";
            this.notifi(type, message, description);
          } else {
            let type = "error";
            let message = "Không tồn tại";
            let description =
              "Tất cả khung theo " +
              this.dataForm.name1 +
              " và " +
              this.dataForm.name2 +
              " đều đã tồn tại";
            this.notifi(type, message, description);
          }
          this.dataFrameMaterials = response.data.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },

    //thay đổi MVL theo khung và chiều cao
    getMaterialByFrameAndHeight() {
      vatLieuAdminService
        .getMaterialByFrameAndHeight(this.dataForm)
        .then((response) => {
          if (response.data.data.length != 0) {
            let type = "success";
            let message = "Thành công";
            let description =
              "Lấy mã vật liệu theo " +
              this.dataForm.name1 +
              " và " +
              this.dataForm.name2 +
              " thành công";
            this.notifi(type, message, description);
          } else {
            let type = "error";
            let message = "Không tồn tại";
            let description =
              "Tất cả mã vật liệu theo " +
              this.dataForm.name1 +
              " và " +
              this.dataForm.name2 +
              " đều đã tồn tại";
            this.notifi(type, message, description);
          }
          this.dataMaterials = response.data.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },

    //thay đổi chiều cao theo MVL và khung
    handleChieuCaoByMVLvaKhung() {
      if (
        this.dataAddFrameHeightMaterial.idHeight != null &&
        this.dataAddFrameHeightMaterial.idHeight != ""
      ) {
        this.checkInputHeight.show = false;
        this.checkInputHeight.message = "";
      } else {
        this.checkInputHeight.show = true;
        this.checkInputHeight.message = "Bạn phải điền chiều cao";
      }

      let data = {
        type: "height",
        id: this.dataAddFrameHeightMaterial.idHeight,
        name: "",
      };
      for (let i = 0; i < this.dataHeights.length; i++) {
        if (
          this.dataHeights[i].id == this.dataAddFrameHeightMaterial.idHeight
        ) {
          data.name = this.dataHeights[i].frameHeight;
        }
      }

      for (let i = this.dataSelect.length - 1; i >= 0; i--) {
        if (this.dataSelect[i].type == "height") {
          this.dataSelect.splice(i, 1);
          break;
        }
      }

      this.dataSelect.push(data);
      console.log("data select", this.dataSelect);
      if (this.dataSelect.length == 2) {
        this.dataForm.id1 = this.dataSelect[0].id;
        this.dataForm.name1 = this.dataSelect[0].name;
        this.dataForm.id2 = data.id;
        this.dataForm.name2 = data.name;
        if (this.dataSelect[0].type == "material") {
          this.getFrameByMaterialAndHeight();
        } else {
          this.getMaterialByFrameAndHeight();
        }
      }
      if (this.dataSelect.length == 3) {
        this.disable = true;
      }
    },

    //thay đổi khung theo MVL và chiều cao
    handleKhungByMVLvaChieuCao() {
      if (
        this.dataAddFrameHeightMaterial.idFrame != null &&
        this.dataAddFrameHeightMaterial.idFrame != ""
      ) {
        this.checkInputFrame.show = false;
        this.checkInputFrame.message = "";
      } else {
        this.checkInputFrame.show = true;
        this.checkInputFrame.message = "Bạn phải điền khung vật liệu";
      }

      let data = {
        type: "frame",
        id: this.dataAddFrameHeightMaterial.idFrame,
        name: "",
      };
      for (let i = 0; i < this.dataFrameMaterials.length; i++) {
        if (
          this.dataFrameMaterials[i].id ==
          this.dataAddFrameHeightMaterial.idFrame
        ) {
          data.name = this.dataFrameMaterials[i].frame;
        }
      }
      for (let i = this.dataSelect.length - 1; i >= 0; i--) {
        if (this.dataSelect[i].type == "frame") {
          this.dataSelect.splice(i, 1);
          break;
        }
      }
      this.dataSelect.push(data);
      console.log("data select", this.dataSelect);
      if (this.dataSelect.length == 2) {
        if (this.dataSelect[0].type == "material") {
          this.dataForm.id1 = this.dataSelect[0].id;
          this.dataForm.name1 = this.dataSelect[0].name;
          this.dataForm.id2 = data.id;
          this.dataForm.name2 = data.name;
          this.getHeightsByMaterialAndFrame();
        } else {
          this.dataForm.id1 = data.id;
          this.dataForm.name1 = data.name;
          this.dataForm.id2 = this.dataSelect[0].id;
          this.dataForm.name2 = this.dataSelect[0].name;
          this.getMaterialByFrameAndHeight();
        }
      }
      if (this.dataSelect.length == 3) {
        this.disable = true;
      }
    },

    //thay đổi MVL theo khung và chiều cao
    handleMVLByKhungvaChieuCao() {
      if (
        this.dataAddFrameHeightMaterial.idMaterial != null &&
        this.dataAddFrameHeightMaterial.idMaterial != ""
      ) {
        this.checkInputCodeMaterial.show = false;
        this.checkInputCodeMaterial.message = "";
      } else {
        this.checkInputCodeMaterial.show = true;
        this.checkInputCodeMaterial.message = "Bạn phải điền mã vật liệu";
      }

      let data = {
        type: "material",
        id: this.dataAddFrameHeightMaterial.idMaterial,
        name: "",
      };
      for (let i = 0; i < this.dataMaterials.length; i++) {
        if (
          this.dataMaterials[i].id == this.dataAddFrameHeightMaterial.idMaterial
        ) {
          data.name = this.dataMaterials[i].name;
        }
      }

      for (let i = this.dataSelect.length - 1; i >= 0; i--) {
        if (this.dataSelect[i].type == "material") {
          this.dataSelect.splice(i, 1);
          break;
        }
      }

      this.dataSelect.push(data);
      console.log("data select", this.dataSelect);
      if (this.dataSelect.length == 2) {
        this.dataForm.id1 = data.id;
        this.dataForm.name1 = data.name;
        this.dataForm.id2 = this.dataSelect[0].id;
        this.dataForm.name2 = this.dataSelect[0].name;
        if (this.dataSelect[0].type == "frame") {
          this.getHeightsByMaterialAndFrame();
        } else {
          this.getFrameByMaterialAndHeight();
        }
      }
      if (this.dataSelect.length == 3) {
        this.disable = true;
      }
    },

    //submit add frame
    submitAddFrame() {
      this.loadingAddFrameHeight = true;
      vatLieuAdminService
        .addFrameHeightMaterial(this.dataAddFrameHeightMaterial)
        .then((response) => {
          this.submitSearch();
          if (response.data.data) {
            let type = "success";
            let message = "Thêm khung và chiều cao mới";
            let description = response.data.message;
            this.notifi(type, message, description);
          } else {
            let type = "error";
            let message = "Thêm khung và chiều cao mới";
            let description = response.data.message;
            this.notifi(type, message, description);
          }
          this.loadingAddFrameHeight = false;
          this.visibleAddFrame = false;
        })
        .catch(() => {
          this.loadingAddFrameHeight = false;
          this.visibleAddFrame = false;
        });
    },

    //add khung
    showModalAddFrame() {
      this.resetFrame();
      this.visibleAddFrame = true;
    },
    checkFormAddFrame() {
      let check = true;
      if (
        this.dataAddFrameHeightMaterial.idMaterial != null &&
        this.dataAddFrameHeightMaterial.idMaterial != ""
      ) {
        this.checkInputCodeMaterial.show = false;
        this.checkInputCodeMaterial.message = "";
      } else {
        check = false;
        this.checkInputCodeMaterial.show = true;
        this.checkInputCodeMaterial.message = "Bạn phải điền mã vật liệu";
      }
      if (
        this.dataAddFrameHeightMaterial.idFrame != null &&
        this.dataAddFrameHeightMaterial.idFrame != ""
      ) {
        this.checkInputFrame.show = false;
        this.checkInputFrame.message = "";
      } else {
        check = false;
        this.checkInputFrame.show = true;
        this.checkInputFrame.message = "Bạn phải điền khung vật liệu";
      }
      if (
        this.dataAddFrameHeightMaterial.idHeight != null &&
        this.dataAddFrameHeightMaterial.idHeight != ""
      ) {
        this.checkInputHeight.show = false;
        this.checkInputHeight.message = "";
      } else {
        check = false;
        this.checkInputHeight.show = true;
        this.checkInputHeight.message = "Bạn phải điền chiều cao";
      }
      if (check) {
        this.submitAddFrame();
      }
    },
    handleCancel() {
      this.visibleAdd = false;
      this.visibleAddUnit = false;
      this.visibleAddFrame = false;
      this.visibleEdit = false;
    },
    notifi(type, message, description) {
      this.$notification[type]({
        message: message,
        description: description,
      });
    },

    //chiều cao
    getAllHeight() {
      heightService
        .getAllFrameHeight(this.dataHeight)
        .then((response) => {
          this.dataHeights = response.data.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    fetchHeight(value) {
      this.dataHeight.frameHeight = value;
      this.getAllHeight();
    },
    //chiều cao

    //nhóm vật liệu
    getAllGroupMaterial() {
      groupMaterialService
        .getAllGroupMaterial(this.dataGroupMaterial)
        .then((response) => {
          this.dataGroupMaterials = response.data.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    fetchGroupMaterial(value) {
      this.dataGroupMaterial.name = value;
      this.getAllGroupMaterial();
    },
    //nhóm vật liệu

    //khung
    getAllFrame() {
      vatLieuAdminService
        .getAllFrame(this.dataFrameMaterial)
        .then((response) => {
          this.dataFrameMaterials = response.data.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    fetchFrame(value) {
      this.dataFrameMaterial.frame = value;
      this.getAllFrame();
    },
    //khung

    //đơn vị
    getAllUnit() {
      unitService
        .getAllUnits(this.dataUnit)
        .then((response) => {
          this.dataUnits = response.data.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    //đơn vị

    //công ty
    getAllCompany() {
      companyService
        .searchCompany(this.dataCompany)
        .then((response) => {
          this.dataCompanys = response.data.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    //công ty

    //xử lý thẻ tag
    handleClose(removedTag) {
      const tags = this.tags.filter((tag) => tag !== removedTag);
      this.tags = tags;
      if (this.tags != null && this.tags.length != 0) {
        this.checkInputCodeMaterial.show = false;
        this.checkInputCodeMaterial.message = "";
      } else {
        this.checkInputCodeMaterial.show = true;
        this.checkInputCodeMaterial.message = "Bạn phải điền mã vật liệu";
      }
    },
    showInput() {
      this.inputVisible = true;
      this.$nextTick(function () {
        this.$refs.input.focus();
      });
    },
    handleInputChange(e) {
      this.inputValue = e.target.value;
    },
    handleInputConfirm() {
      const inputValue = this.inputValue;
      let tags = this.tags;
      if (inputValue.trim() != "" && tags.indexOf(inputValue) === -1) {
        tags = [...tags, inputValue.trim()];
      }
      Object.assign(this, {
        tags,
        inputVisible: false,
        inputValue: "",
      });
      if (this.tags != null && this.tags.length != 0) {
        this.checkInputCodeMaterial.show = false;
        this.checkInputCodeMaterial.message = "";
      } else {
        this.checkInputCodeMaterial.show = true;
        this.checkInputCodeMaterial.message = "Bạn phải điền mã vật liệu";
      }
    },
    //xử lý thẻ tag
  },
};
</script>

<style scoped>
/* button icon */
#delete {
  background-color: rgb(255, 0, 0);
  color: white;
}
#delete:hover {
  background-color: rgba(233, 15, 15, 0.863);
  color: white;
}
#edit {
  background-color: rgb(10, 208, 243);
  color: white;
}
#edit:hover {
  background-color: rgb(0, 181, 253);
  color: white;
}
</style>