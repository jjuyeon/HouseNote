import Axios from "axios";
import HappyHouseApiMap from "./HappyHouseApiMap";
import store from "../store";

export { EndPoint } from "./HappyHouseApiMap";
export const HttpMethod = {
  DELETE: "delete",
  GET: "get",
  POST: "post",
  PUT: "put",
  PATCH: "patch",
};

export default class HappyHouseApi {
  static request({ data, query, path, method, url }) {
    try {
      if (!method || !url) {
        throw new Error("HappyHouseApi needs url and method");
      }

      const api = HappyHouseApiMap[method][url];
      if (api === undefined) {
        throw new Error(
          `HappyHouseApi does not have the mapping ${method}, ${url}`
        );
      }

      if (method === HttpMethod.GET) {
        if (path) {
          url = url + `/${path.id}`;
        }

        if (query) {
          url =
            url +
            "?" +
            Object.keys(query)
              .map((key) => key + "=" + query[key])
              .join("&");
        }

        return Axios.get(url, {
          headers: {
            "Access-Control-Allow-Origin": "*",
            "Content-Type": "application/json",
            Authorization: store.state.userToken,
          },
        });
      }

      if (method === HttpMethod.POST) {
        if (path) {
          url = url + `/${path.id}`;
        }
        return Axios.post(url, data, {
          headers: {
            "Access-Control-Allow-Origin": "*",
            "Content-Type": "application/json",
            Authorization: store.state.userToken,
          },
        });
      }

      if (method === HttpMethod.DELETE) {
        if (path) {
          url = url + `/${path.id}`;
        }

        if (query) {
          url =
            url +
            "?" +
            Object.keys(query)
              .map((key) => key + "=" + query[key])
              .join("&");
        }

        return Axios.delete(url, {
          headers: {
            "Access-Control-Allow-Origin": "*",
            "Content-Type": "application/json",
            Authorization: store.state.userToken,
          },
        });
      }

      if (method === HttpMethod.PUT) {
        if (path) {
          url = url + `/${path.id}`;
        }

        if (query) {
          url =
            url +
            "?" +
            Object.keys(query)
              .map((key) => key + "=" + query[key])
              .join("&");
        }

        return Axios.put(url, data, {
          headers: {
            "Access-Control-Allow-Origin": "*",
            "Content-Type": "application/json",
            Authorization: store.state.userToken,
          },
        });
      }

      if (method === HttpMethod.PATCH) {
        if (path) {
          url = url + `/${path.id}`;
        }

        if (query) {
          url =
            url +
            "?" +
            Object.keys(query)
              .map((key) => key + "=" + query[key])
              .join("&");
        }

        return Axios.patch(url, data, {
          headers: {
            "Access-Control-Allow-Origin": "*",
            "Content-Type": "application/json",
            Authorization: store.state.userToken,
          },
        });
      }
    } catch (err) {
      return "axios cannot be created";
    }
  }
}
