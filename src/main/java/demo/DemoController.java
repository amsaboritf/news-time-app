/*
 * Copyright 2015 Benedikt Ritter
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/")
public class DemoController {

    private RecordRepository repository;

    /*@Autowired
    private RecordDAO recordDAO;*/

    @Autowired
    public DemoController(RecordRepository repository) {
        this.repository = repository;
    }


    @RequestMapping(method = RequestMethod.GET)
    public String home(ModelMap model) {
        List<Record> records = repository.findAll();
        //List<Record> records = recordDAO.findAll();
        model.addAttribute("records", records);
        model.addAttribute("insertRecord", new Record());
        return "home";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String insertData(ModelMap model, 
                             @ModelAttribute("insertRecord") @Valid Record record,
                             BindingResult result) {
        if (!result.hasErrors()) {
//            recordDAO.merge(record);
          repository.save(record);
        }
        return home(model);
    }
}
