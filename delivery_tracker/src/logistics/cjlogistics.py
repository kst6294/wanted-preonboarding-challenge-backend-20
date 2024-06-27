import enum
import json
from dataclasses import dataclass, field
from http.cookies import SimpleCookie
from typing import List
from urllib.parse import urlencode

import requests
from bs4 import BeautifulSoup


@dataclass
class DeliveryStatus:
    nsDlvNm: str
    crgNm: str
    crgSt: str
    dTime: str
    empImgNm: str
    regBranId: str
    regBranNm: str
    scanNm: str


@dataclass
class TrackingResult:
    invcNo: str
    sendrNm: str
    qty: str
    itemNm: str
    rcvrNm: str
    rgmailNo: str
    oriTrspbillnum: str
    rtnTrspbillnum: str
    nsDlvNm: str
    delivery_status: List[DeliveryStatus] = field(default_factory=list)

    @property
    def latest_status(self) -> DeliveryStatus:
        try:
            return self.delivery_status[-1]
        except Exception:
            None


class DeliveryStatusEnum(enum.Enum):
    PICKED_UP = "11"  # 집화처리
    IN_TRANSIT_TO_DISTRIBUTION = "21"  # SM입고
    IN_TRANSIT_TO_TERMINAL = "41"  # 간선상차
    IN_TRANSIT_TO_DESTINATION = "44"  # 간선상차
    ARRIVED_AT_DESTINATION = "42"  # 간선하차
    OUT_FOR_DELIVERY = "82"  # 배송출발
    DELIVERED = "91"  # 배송완료


class CJLogistics:
    MAIN_URL = "https://www.cjlogistics.com/ko/tool/parcel/tracking"
    DETAIL_URL = "https://www.cjlogistics.com/ko/tool/parcel/tracking-detail"

    def __get_main_page(self):
        main_page_response = requests.get(self.MAIN_URL)
        return main_page_response

    def __get_headers(self, main_page_response):
        cookie_headers = "; ".join(
            [
                f"{cookie.key}={cookie.value}"
                for cookie in SimpleCookie(
                    main_page_response.headers.get("Set-Cookie", "")
                ).values()
            ]
        )
        return {"Cookie": cookie_headers}

    def __get_csrf(self, main_page_response):
        soup = BeautifulSoup(main_page_response.text, "html.parser")
        csrf_token = soup.find("input", {"name": "_csrf"}).get("value")
        return csrf_token

    def track(self, track_number):
        main_page_response = self.__get_main_page()
        headers = self.__get_headers(main_page_response)
        csrf_token = self.__get_csrf(main_page_response)
        tracking_number = track_number
        query_params = {
            "paramInvcNo": tracking_number,
            "_csrf": csrf_token,
        }
        tracking_detail_url = f"{self.DETAIL_URL}?{urlencode(query_params)}"
        tracking_detail_response = requests.post(tracking_detail_url, headers=headers)

        response_data = json.loads(tracking_detail_response.text)

        tracking_result = TrackingResult(
            **response_data["parcelResultMap"]["resultList"][0],
            delivery_status=[
                DeliveryStatus(**item)
                for item in response_data["parcelDetailResultMap"]["resultList"]
            ],
        )

        return tracking_result
