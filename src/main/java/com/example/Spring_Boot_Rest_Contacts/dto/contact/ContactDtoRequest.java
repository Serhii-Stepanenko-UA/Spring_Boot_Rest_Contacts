package com.example.Spring_Boot_Rest_Contacts.dto.contact;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Java record -клас, мета якого зберігати дані та надавати
 * деякі спеціальні функції, наприклад, немає потреби описувати
 * конструктор, гетер, equals, hashCode, оскільки в рекорді ці речі будуть
 * автоматично згенеровані компілятором
 */
// @JsonIgnoreProperties допомагає уникнути помилки
// при створенні об'єкта цього типу, якщо якесь поле є null
@JsonIgnoreProperties(ignoreUnknown = true)
public record ContactDtoRequest(Long id, String firstName,
                                String lastName, String phone) {
}
