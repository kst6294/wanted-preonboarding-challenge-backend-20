import enum

from logistics.cjlogistics import CJLogistics


class CodeEnum(enum.Enum):
    CJGLS = "01"

class DeliveryTracker:
    def track(self, code, track_number):
        try:
            match CodeEnum(code):
                case CodeEnum.CJGLS:
                    return CJLogistics().track(track_number=track_number)

        except Exception:
            return None
