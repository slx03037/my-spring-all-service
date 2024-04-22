//import java.util.List;
//
///**
// * @author shenlx
// * @description
// * @date 2024/4/17 15:24
// */
//public class Demo {
//    public void handler(){
//        userService.getFavorites(userId, new Callback<List<String>>() {
//            public void onSuccess(List<String> list) {
//                if (list.isEmpty()) {
//                    suggestionService.getSuggestions(new Callback<List<Favorite>>() {
//                        public void onSuccess(List<Favorite> list) {
//                            UiUtils.submitOnUiThread(() -> {
//                                list.stream()
//                                        .limit(5)
//                                        .forEach(uiList::show);
//                            });
//                        }
//
//                        public void onError(Throwable error) {
//                            UiUtils.errorPopup(error);
//                        }
//                    });
//                } else {
//                    list.stream()
//                            .limit(5)
//                            .forEach(favId -> favoriteService.getDetails(favId,
//                                    new Callback<Favorite>() {
//                                        public void onSuccess(Favorite details) {
//                                            UiUtils.submitOnUiThread(() -> uiList.show(details));
//                                        }
//
//                                        public void onError(Throwable error) {
//                                            UiUtils.errorPopup(error);
//                                        }
//                                    }
//                            ));
//                }
//            }
//
//            public void onError(Throwable error) {
//                UiUtils.errorPopup(error);
//            }
//        });
//    }
//}
