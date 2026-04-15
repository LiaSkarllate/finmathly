import ModalitiesStore from "./Definitions/ModalitiesStore";
import { MessageStore } from "./MessageStore";

export default class RootStore {
	messageStore = new MessageStore();
	modalitiesStore = new ModalitiesStore();
}