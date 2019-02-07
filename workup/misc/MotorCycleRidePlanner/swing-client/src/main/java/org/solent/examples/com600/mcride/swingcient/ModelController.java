/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.examples.com600.mcride.swingcient;

import org.solent.examples.com600.mcride.model.Entity;

/**
 *
 * @author cgallen
 */
public interface ModelController {

    void clearSearch();

    void findMatchingSearch(Entity templateEntity);

    EntityListTableModel getEntityListTableModel();
    
    boolean forceReloadData();
    
}
