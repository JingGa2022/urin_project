import { Alert } from "@mui/material";
import {
  createAsyncThunk,
  createSlice,
  isRejectedWithValue,
} from "@reduxjs/toolkit";
import axios from "axios";

export const getReport = createAsyncThunk(
  "GET_REPORT",
  async ({ meetingId, navigate }) => {
    console.log(meetingId);
    try {
      console.log("GET_REPORT");
      const response = await axios.get(
        `${process.env.REACT_APP_BACK_BASE_URL}meeting/${meetingId}/report`,
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
          },
        }
      );
      console.log(response.data);
      return response.data;
    } catch (err) {
      return isRejectedWithValue(err.response.data);
    }
  }
);

const reportSlice = createSlice({
  name: "reports",
  initialState: {
    aiData: {
      interviewee: {
        emotion: {
          confidence: 100,
          calm: 100,
          nervous: 0,
        },
        poseDataList: [
          {
            name: "기준자세1",
            count: 1,
          },
          {
            name: "기준자세2",
            count: 2,
          },
          {
            name: "기준자세3",
            count: 3,
          },
          {
            name: "기준자세4",
            count: 4,
          },
        ],
      },
      passUser: {
        emotion: {
          confidence: 100,
          calm: 100,
          nervous: 0,
        },
        poseDataList: [
          {
            name: "기준자세1",
            count: 1,
          },
          {
            name: "기준자세2",
            count: 2,
          },
          {
            name: "기준자세3",
            count: 3,
          },
          {
            name: "기준자세4",
            count: 4,
          },
        ],
      },
    },
    feedback: {
      personality: [
        {
          questionContent: "인성면접질문",
          answerContentList: [
            {
              interviewer: "인성면접관",
              content: "인성면접답변",
            },
          ],
        },
      ],
      tech: [
        {
          questionContent: "적성면접질문",
          answerContentList: [
            {
              interviewer: "적성면접관",
              content: "적성면접답변",
            },
          ],
        },
      ],
    },
  },
  reducers: {},
  extraReducers: {
    [getReport.fulfilled]: (state, { payload }) => payload,
  },
});

export default reportSlice.reducer;
