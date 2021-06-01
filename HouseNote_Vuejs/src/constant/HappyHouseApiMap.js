const API = process.env.VUE_APP_API_URL;
export const EndPoint = {
  AUTH_API_LOGINTOKEN: `${API}/api/user/logintoken`,
  AUTH_API_KAKAOLOGIN: `${API}/api/user/kakaologin`,
  AUTH_API_KAKAOUNLINK: `${API}/api/user`,
  AUTH_API_BOOKMARK: `${API}/api/bookmark`,
  AUTH_API_NOTE: `${API}/api/note`,
  AUTH_API_HOUSE: `${API}/api/house`,
  AUTH_API_HOUSEDEAL: `${API}/api/housedeal`,
  AUTH_API_SIDO: `${API}/api/sido`,
  AUTH_API_GUGUN: `${API}/api/gugun`,
  AUTH_API_DONG: `${API}/api/dong`,
  AUTH_API_BOARD: `${API}/api/board`,
};

const HappyHouseApiMap = {
  post: {
    [EndPoint.AUTH_API_KAKAOLOGIN]: {
      data: {},
    },
    [EndPoint.AUTH_API_BOOKMARK]: {
      data: {},
    },
    [EndPoint.AUTH_API_BOARD]: {
      data: {},
    },
  },
  get: {
    [EndPoint.AUTH_API_LOGINTOKEN]: {
      data: {},
    },
    [EndPoint.AUTH_API_BOOKMARK]: {
      data: {},
    },
    [EndPoint.AUTH_API_NOTE]: {
      data: {},
    },
    [EndPoint.AUTH_API_HOUSE]: {
      data: {},
    },
    [EndPoint.AUTH_API_HOUSEDEAL]: {
      data: {},
    },
    [EndPoint.AUTH_API_SIDO]: {
      data: {},
    },
    [EndPoint.AUTH_API_GUGUN]: {
      data: {},
    },
    [EndPoint.AUTH_API_DONG]: {
      data: {},
    },
    [EndPoint.AUTH_API_BOARD]: {
      data: {},
    },
  },
  delete: {
    [EndPoint.AUTH_API_KAKAOUNLINK]: {
      data: {},
    },
    [EndPoint.AUTH_API_BOOKMARK]: {
      data: {},
    },
    [EndPoint.AUTH_API_BOARD]: {
      data: {},
    },
  },
  put: {
    [EndPoint.AUTH_API_BOARD]: {
      data: {},
    },
  },
  patch: {
    [EndPoint.AUTH_API_NOTE]: {
      data: {},
    },
  },
};

export default HappyHouseApiMap;
